package faang.school.accountservice.model.account.numbers;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@AllArgsConstructor
@Data
public class FreeAccountId {

    @Column(name = "type", nullable = false, length = 32)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

    @Column(name = "account_number", nullable = false)
    private long accountNumber;
}
