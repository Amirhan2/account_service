package faang.school.accountservice.service.impl;

import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.dto.AccountDto;
import faang.school.accountservice.model.entity.Account;
import faang.school.accountservice.model.enums.AccountStatus;
import faang.school.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional(readOnly = true)
    @Override
    public AccountDto getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional(readOnly = true)
    @Override
    public AccountDto getAccountNumber(String number) {
        Account account = accountRepository.findAccountByNumber(number).orElseThrow();

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto openAccount(AccountDto accountDto) {
        Account account = accountMapper.accountDtoToAccount(accountDto);

        return accountMapper.accountToAccountDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountDto blockAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setStatus(AccountStatus.BLOCKED);

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto blockAccountNumber(String number) {
        Account account = accountRepository.findAccountByNumber(number).orElseThrow();
        account.setStatus(AccountStatus.BLOCKED);

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    @Override
    public List<AccountDto> blockAllAccountsByUserOrProject(Long userId, Long projectId) {
        List<Account> accounts;
        if (userId != null) {
            accounts = accountRepository.findAllByUserId(userId);
        } else {
            accounts = accountRepository.findAllByProjectId(projectId);
        }
        if (!accounts.isEmpty()) {
            accounts.forEach(account -> account.setStatus(AccountStatus.BLOCKED));
        }

        return accountMapper.accountListToAccountDtoList(accounts);
    }

    @Transactional
    @Override
    public AccountDto unblockAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setStatus(AccountStatus.ACTIVE);

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto unblockAccountNumber(String number) {
        Account account = accountRepository.findAccountByNumber(number).orElseThrow();
        account.setStatus(AccountStatus.ACTIVE);

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    @Override
    public List<AccountDto> unblockAllAccountsByUserOrProject(Long userId, Long projectId) {
        List<Account> accounts;
        if (userId != null) {
            accounts = accountRepository.findAllByUserId(userId);
        } else {
            accounts = accountRepository.findAllByProjectId(projectId);
        }
        if (!accounts.isEmpty()) {
            accounts.forEach(account -> account.setStatus(AccountStatus.ACTIVE));
        }

        return accountMapper.accountListToAccountDtoList(accounts);
    }

    @Transactional
    @Override
    public AccountDto closeAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setStatus(AccountStatus.CLOSED);

        return accountMapper.accountToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto closeAccountNumber(String number) {
        Account account = accountRepository.findAccountByNumber(number).orElseThrow();
        account.setStatus(AccountStatus.CLOSED);

        return accountMapper.accountToAccountDto(account);
    }
}