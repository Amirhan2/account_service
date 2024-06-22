package faang.school.accountservice.controller.balance;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.service.balance.BalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/balances")
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("/{balanceId}")
    public BalanceDto getBalance(@PathVariable long balanceId) {
        return balanceService.getBalance(balanceId);
    }

    @PostMapping("/{accountId}")
    public BalanceDto createBalance(@PathVariable long accountId) {
        return balanceService.createBalance(accountId);
    }

    @PutMapping()
    public BalanceDto updateBalance(@RequestBody @Valid BalanceDto balanceDto) {
        return balanceService.updateBalance(balanceDto);
    }

    @DeleteMapping("/{balanceId}")
    public void deleteBalance(@PathVariable long balanceId) {
        balanceService.deleteBalance(balanceId);
    }
}
