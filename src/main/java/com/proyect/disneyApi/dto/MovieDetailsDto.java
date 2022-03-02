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
public class MovieDetailsDto {

    private Long id;
    private String image;
    private String title;
    private String gender;
    private String year;
    private String rating;
    private Set<CharacterDto> characters;
}
