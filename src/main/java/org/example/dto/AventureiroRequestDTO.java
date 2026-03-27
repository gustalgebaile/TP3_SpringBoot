package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.enums.ClasseAventureiro;

public record AventureiroRequestDTO(

        @NotBlank(message = "O nome do aventureiro não pode estar vazio.")
        @Size(max = 120, message = "O nome não pode ultrapassar 120 caracteres.")
        String nome,

        @NotNull(message = "A classe do aventureiro é obrigatória.")
        ClasseAventureiro classe,

        @NotNull(message = "O nível é obrigatório.")
        @Min(value = 1, message = "O nível mínimo do aventureiro é 1.")
        Integer nivel,

        @NotNull(message = "O ID da organização é obrigatório.")
        Long organizacaoId,

        @NotNull(message = "O ID do usuário responsável é obrigatório.")
        Long usuarioId
) {}