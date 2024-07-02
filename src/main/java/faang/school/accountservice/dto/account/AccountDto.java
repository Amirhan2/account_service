package faang.school.accountservice.dto.account;

import faang.school.accountservice.dto.OwnerDto;
import faang.school.accountservice.model.enums.AccountType;
import faang.school.accountservice.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private String number;
    private OwnerDto owner;
    private AccountType accountType;
    private Currency currency;
}