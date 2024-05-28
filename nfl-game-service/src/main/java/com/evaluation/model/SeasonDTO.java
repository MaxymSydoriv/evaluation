package com.evaluation.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SeasonDTO {

    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
