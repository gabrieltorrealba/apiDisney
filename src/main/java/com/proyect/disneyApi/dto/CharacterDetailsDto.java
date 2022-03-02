package com.proyect.disneyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CharacterDetailsDto {

    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Float weight;
    private String history;
    private Set<MovieDto> movies;
}
