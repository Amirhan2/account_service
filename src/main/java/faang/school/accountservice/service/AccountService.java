package faang.school.accountservice.service;

import faang.school.accountservice.dto.account.AccountDto;
import faang.school.accountservice.mapper.account.AccountMapper;
import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.model.account.AccountStatus;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validation.AccountValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountValidator accountValidator;

    public AccountDto open(long userId, AccountDto accountDto) {
        accountDto.setOwnerId(userId);
        accountDto.setAccountStatus(AccountStatus.ACTIVE);
        Account openedAccount = accountRepository.save(accountMapper.toEntity(accountDto));
        return accountMapper.toDto(openedAccount);
    }

    public AccountDto get(long userId, long accountId) {
        accountValidator.validateAccountOwner(userId, accountId);
        Account account = getAccountFromRepository(accountId);
        return accountMapper.toDto(account);
    }

    public List<AccountDto> getAllOwnerAccounts(long ownerId) {
        List<Account> accounts = accountRepository.findByOwnerId(ownerId);
        return accountMapper.toDto(accounts);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 3000, multiplier = 1.5))
    public void block(long userId, long accountId) {
        accountValidator.validateAccountOwner(userId, accountId);
        Account account = getAccountFromRepository(accountId);
        account.setAccountStatus(AccountStatus.INACTIVE);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 3000, multiplier = 1.5))
    public void close(long userId, long accountId) {
        accountValidator.validateAccountOwner(userId, accountId);
        Account account = getAccountFromRepository(accountId);
        account.setAccountStatus(AccountStatus.CLOSED);
    }

    public Account getAccountById(long accountId) {
        return getAccountFromRepository(accountId);
    }

    private Account getAccountFromRepository(long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new EntityNotFoundException("Account doesn't exist by id " + accountId));
        }
}
