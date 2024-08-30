package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.account.Account;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountMapperTest {
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private Account account;
    private Account expectedAccount;
    private AccountDto accountDto;
    private AccountDto expectedDto;

    void setUp() {
        UUID accountId = UUID.randomUUID();
        String accountNumber = "12345678901234567890";
        LocalDateTime now = LocalDateTime.now();

        account = Account.builder()
                .id(accountId)
                .accountNumber(accountNumber)
                .ownerId(1L)
                .ownerType(OwnerType.USER)
                .accountType(AccountType.SAVINGS)
                .currency(Currency.USD)
                .status(AccountStatus.ACTIVE)
                .balance(new BigDecimal("1000.00"))
                .transactionLimit(new BigDecimal("500.00"))
                .version(1L)
                .createdAt(now)
                .updatedAt(now)
                .closedAt(null)
                .build();

        expectedAccount = Account.builder()
                .id(accountId)
                .accountNumber(accountNumber)
                .ownerId(1L)
                .ownerType(OwnerType.USER)
                .accountType(AccountType.SAVINGS)
                .currency(Currency.USD)
                .status(AccountStatus.ACTIVE)
                .balance(new BigDecimal("1000.00"))
                .transactionLimit(new BigDecimal("500.00"))
                .version(1L)
                .createdAt(now)
                .updatedAt(now)
                .closedAt(null)
                .build();

        accountDto = AccountDto.builder()
                .id(accountId)
                .accountNumber(accountNumber)
                .ownerId(1L)
                .ownerType("INDIVIDUAL")
                .accountType("SAVINGS")
                .currency("USD")
                .status("ACTIVE")
                .balance(new BigDecimal("1000.00"))
                .transactionLimit(new BigDecimal("500.00"))
                .version(1L)
                .createdAt(now)
                .updatedAt(now)
                .closedAt(null)
                .build();

        expectedDto = AccountDto.builder()
                .id(accountId)
                .accountNumber(accountNumber)
                .ownerId(1L)
                .ownerType("INDIVIDUAL")
                .accountType("SAVINGS")
                .currency("USD")
                .status("ACTIVE")
                .balance(new BigDecimal("1000.00"))
                .transactionLimit(new BigDecimal("500.00"))
                .version(1L)
                .createdAt(now)
                .updatedAt(now)
                .closedAt(null)
                .build();
    }

    @Test
    @DisplayName("Проверка преобразования Entity to DTO")
    public void testAccountToAccountDto() {
        AccountDto resultDto = accountMapper.toDto(account);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    @DisplayName("Проверка преобразования DTO to Entity")
    public void testAccountDtoToAccount() {
        Account resultAccount = accountMapper.toEntity(accountDto);
        assertEquals(expectedAccount, resultAccount);
    }
}