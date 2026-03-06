package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.enums.ClasseAventureiro;

public record AventureiroRequestDTO(
        @NotBlank(message = "O nome é obrigatório e não pode ser vazio")
        String nome,

        @NotNull(message = "A classe é obrigatória (GUERREIRO, MAGO, ARQUEIRO, CLERIGO, LADINO)")
        ClasseAventureiro classe,

        @NotNull(message = "O nível é obrigatório")
        @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
        Integer nivel
) {}
