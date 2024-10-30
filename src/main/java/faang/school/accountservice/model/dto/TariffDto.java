package faang.school.accountservice.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TariffDto {
    private Long id;

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "rate cannot be null")
    @Positive(message = "rate must be positive")
    private Double rate;
}