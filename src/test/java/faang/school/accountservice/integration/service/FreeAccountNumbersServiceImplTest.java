package faang.school.accountservice.integration.service;

import faang.school.accountservice.IntegrationBaseTest;
import faang.school.accountservice.model.entity.FreeAccountNumberId;
import faang.school.accountservice.model.enums.AccountType;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import faang.school.accountservice.service.FreeAccountNumbersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreeAccountNumbersServiceImplTest extends IntegrationBaseTest {
    private static final String EXISTING_ACCOUNT_NUMBER = "42000000000000000000";
    private static final String FIRST_CREDIT_OVERDUE_CREDIT = "40000000000000000000";
    @Autowired
    private FreeAccountNumbersService accountNumbersService;
    @Autowired
    private FreeAccountNumbersRepository freeAccNumberRepo;
    @Autowired
    private AccountNumbersSequenceRepository sequenceRepository;

    @Test
    void getUniqueAccountNumber_takeExisting() {
        assertTrue(freeAccNumberRepo.existsById(new FreeAccountNumberId(AccountType.INDIVIDUAL,
                EXISTING_ACCOUNT_NUMBER)));

        accountNumbersService.getUniqueAccountNumber(accNumber -> {}, AccountType.INDIVIDUAL);

        assertFalse(freeAccNumberRepo.existsById(new FreeAccountNumberId(AccountType.INDIVIDUAL,
                EXISTING_ACCOUNT_NUMBER)));
    }

    @Test
    void getUniqueAccountNumber_generateNew() {
//        assertTrue(sequenceRepository.findByAccountType(AccountType.CREDIT_OVERDUE).isEmpty());
        FreeAccountNumberId freeAccountNumberId = new FreeAccountNumberId(AccountType.CREDIT_OVERDUE,
                FIRST_CREDIT_OVERDUE_CREDIT);

        accountNumbersService.getUniqueAccountNumber(accNumber -> {
            assertEquals(freeAccountNumberId, accNumber.getId());
        }, AccountType.CREDIT_OVERDUE);

        assertTrue(sequenceRepository.findByAccountType(AccountType.CREDIT_OVERDUE).isPresent());
//        assertEquals(1, sequenceRepository.findByAccountType(AccountType.CREDIT_OVERDUE).get().getCounter());
    }
}
