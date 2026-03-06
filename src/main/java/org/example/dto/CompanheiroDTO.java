package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.enums.EspecieCompanheiro;

public record CompanheiroDTO(
        @NotBlank(message = "O nome do companheiro é obrigatório")
        String nome,

        @NotNull(message = "A espécie é obrigatória (LOBO, CORUJA, GOLEM, DRAGAO_MINIATURA)")
        EspecieCompanheiro especie,

        @NotNull(message = "A lealdade é obrigatória")
        @Min(value = 0, message = "A lealdade não pode ser menor que 0")
        @Max(value = 100, message = "A lealdade não pode ser maior que 100")
        Integer lealdade
) {}
