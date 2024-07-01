package faang.school.accountservice.service.account_number.generation;

import faang.school.accountservice.model.enums.AccountType;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import faang.school.accountservice.service.account_number.AccountNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountNumberGenerationServiceImpl implements AccountNumberGenerationService {

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumberService accountNumberService;

    @Override
    @Transactional
    public void fillFreeNumbersTo(long amount, AccountType accountType) {

        long currentNumbersAmount = freeAccountNumbersRepository.countByType(accountType);
        long dif = amount - currentNumbersAmount;

        if (dif <= 0) {
            throw new RuntimeException("There are no free accounts: currentNumbersAmount=" + currentNumbersAmount + ", dif=" + dif);
        }

        for (long i = 0; i < dif; i++) {
            accountNumberService.generateAccountNumber(accountType);
        }
    }

}
