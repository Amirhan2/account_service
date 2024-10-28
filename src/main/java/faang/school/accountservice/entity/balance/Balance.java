package faang.school.accountservice.entity.balance;

import faang.school.accountservice.entity.account.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "authorization_balance")
    private long authorizationBalance;

    @Column(name = "actual_balance", nullable = false)
    private long actualBalance;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    //@Temporal(TemporalType.TIMESTAMP) есть ли смысл добавлять данную строку? как я понимаю JPA и так сохранит в TIMESTAMP тип LocalDateTime.
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private int version;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
