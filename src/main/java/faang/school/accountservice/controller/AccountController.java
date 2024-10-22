package faang.school.accountservice.controller;

import faang.school.accountservice.model.dto.AccountDto;
import faang.school.accountservice.service.impl.AccountServiceImpl;
import faang.school.accountservice.validator.AccountControllerValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountControllerValidator validator;
    private final AccountServiceImpl accountService;

    @Operation(summary = "Get account", description = "Get account by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @Operation(summary = "Get account by number", description = "Get account by number")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/number/{number}")
    public AccountDto getAccountNumber(@PathVariable String number) {
        return accountService.getAccountNumber(number);
    }

    @Operation(summary = "Create account", description = "Create account in DB")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public AccountDto openAccount(@RequestBody AccountDto accountDto) {
        validator.checkDto(accountDto);

        return accountService.openAccount(accountDto);
    }

    @Operation(summary = "Block account", description = "Block account by Id")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block/{id}")
    public AccountDto blockAccount(@PathVariable Long id) {
        return accountService.blockAccount(id);
    }

    @Operation(summary = "Block account by account number", description = "Block account by account number")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block/number/{number}")
    public AccountDto blockAccountNumber(@PathVariable String number) {
        return accountService.blockAccountNumber(number);
    }

    @Operation(summary = "Block all user or project accounts ", description = "Block all user or project accounts")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/block")
    public List<AccountDto> blockAccountsByUserOrProject(@RequestParam(required = false) Long userId,
                                                         @RequestParam(required = false) Long projectId) {
        validator.checkParams(userId, projectId);
        return accountService.blockAllAccountsByUserOrProject(userId, projectId);
    }

    @Operation(summary = "Unblock account", description = "Unblock account by Id")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock/{id}")
    public AccountDto unblockAccount(@PathVariable Long id) {
        return accountService.unblockAccount(id);
    }

    @Operation(summary = "Unblock account by account number", description = "Unblock account by account number")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock/number/{number}")
    public AccountDto unblockAccountNumber(@PathVariable String number) {
        return accountService.unblockAccountNumber(number);
    }

    @Operation(summary = "Unblock all user or project accounts ", description = "Unblock all user or project accounts")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/unblock")
    public List<AccountDto> unblockAccountsByUserOrProject(@RequestParam(required = false) Long userId,
                                                           @RequestParam(required = false) Long projectId) {
        validator.checkParams(userId, projectId);
        return accountService.unblockAllAccountsByUserOrProject(userId, projectId);
    }

    @Operation(summary = "Close account", description = "Close account by Id")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/close/{id}")
    public AccountDto closeAccount(@PathVariable Long id) {
        return accountService.closeAccount(id);
    }

    @Operation(summary = "Close account", description = "Close account by number")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/close/number/{number}")
    public AccountDto closeAccountNumber(@PathVariable String number) {
        return accountService.closeAccountNumber(number);
    }
}
