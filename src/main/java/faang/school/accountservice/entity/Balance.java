package faang.school.accountservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "balances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private long accountNumber;

    @Column(name = "authorization_balance")
    private long authorizationBalance;

    @Column(name = "actual_balance")
    private long actualBalance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "change_at")
    private LocalDateTime changeAt;

    @Version
    private int version;
}