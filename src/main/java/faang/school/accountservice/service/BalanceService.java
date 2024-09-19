package faang.school.accountservice.service;

import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.enums.PaymentStatus;
import faang.school.accountservice.exception.NotEnoughMoneyAuthorizationException;
import faang.school.accountservice.repository.BalanceRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceService {
    private final BalanceRepository balanceRepository;

    @Value("${spring.limits.user-limit}")
    private BigDecimal userLimit;
    @Value("${spring.limits.bank-limit}")
    private BigDecimal bankLimit;

    @Transactional
    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2.0)
    )
    public PaymentStatus transferToAuthorizedBalance(Balance balance, BigDecimal sum) {
        PaymentStatus status = isBalanceSufficient(sum, balance.getBalance());
        if (status == PaymentStatus.SUCCESS) {
            balance.setAuthorizationBalance(balance.getAuthorizationBalance().add(sum));
            balance.setBalance(balance.getBalance().subtract(sum));
            balanceRepository.save(balance);
        } else {
            log.info("Authorization cannot be completed, there are not enough funds {}", sum);
            throw new NotEnoughMoneyAuthorizationException("You haven't got enough money for authorization");
        }
        return status;
    }

    @Transactional
    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2.0)
    )
    public Balance createBalance() {
        return balanceRepository.save(new Balance());
    }

    @Transactional
    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2.0)
    )
    public PaymentStatus receiveFunds(Balance balance, BigDecimal sum) {
        balance.setBalance(balance.getBalance().add(sum));
        balanceRepository.save(balance);
        return PaymentStatus.SUCCESS;
    }

    @Transactional
    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2.0)
    )
    public PaymentStatus spendFromAuthorizedBalance(Balance balance, BigDecimal sum) {
        PaymentStatus status = isBalanceSufficient(sum, balance.getAuthorizationBalance());
        if (status == PaymentStatus.SUCCESS) {
            balance.setBalance(balance.getBalance().subtract(sum));
            balanceRepository.save(balance);
        }
        return status;
    }

    @Transactional
    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2.0)
    )
    public PaymentStatus processPayment(Balance balance, BigDecimal sum) {
        PaymentStatus status = isBalanceSufficient(sum, balance.getBalance());
        if (status == PaymentStatus.SUCCESS) {
            balance.setBalance(balance.getBalance().subtract(sum));
            balanceRepository.save(balance);
        }
        return status;
    }

    private PaymentStatus isBalanceSufficient(BigDecimal paySum, BigDecimal balanceSum) {
        if (balanceSum.compareTo(paySum) < 0) {
            return PaymentStatus.INSUFFICIENT_BALANCE;
        }
        if (paySum.compareTo(userLimit) > 0) {
            return PaymentStatus.USER_LIMIT_EXCEEDED;
        }
        if (paySum.compareTo(bankLimit) > 0) {
            return PaymentStatus.BANK_LIMIT_EXCEEDED;
        }
        return PaymentStatus.SUCCESS;
    }

}
