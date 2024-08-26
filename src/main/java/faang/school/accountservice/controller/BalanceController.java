package faang.school.accountservice.controller;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping
    public BalanceDto createBalance(@RequestBody BalanceDto balanceDto) {
        return balanceService.createBalance(balanceDto);
    }

    @GetMapping("/{accountId}")
    public BalanceDto getBalance(@PathVariable long accountId) {
        return balanceService.getBalance(accountId);
    }

    @PutMapping
    public BalanceDto updateBalance(@RequestBody BalanceDto balanceDto) {
        return balanceService.updateBalance(balanceDto);
    }
}
