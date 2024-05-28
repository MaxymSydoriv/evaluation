package com.evaluation.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerDTO {

    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Integer teamId;
}
