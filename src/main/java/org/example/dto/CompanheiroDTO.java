package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.enums.EspecieCompanheiro;

public record CompanheiroDTO(

        @NotBlank(message = "O nome do companheiro não pode estar vazio.")
        @Size(max = 120, message = "O nome não pode ultrapassar 120 caracteres.")
        String nome,

        @NotNull(message = "A espécie do companheiro é obrigatória.")
        EspecieCompanheiro especie,

        @NotNull(message = "O nível de lealdade é obrigatório.")
        @Min(value = 0, message = "A lealdade não pode ser menor que 0.")
        @Max(value = 100, message = "A lealdade não pode ser maior que 100.")
        Integer lealdade
) {}