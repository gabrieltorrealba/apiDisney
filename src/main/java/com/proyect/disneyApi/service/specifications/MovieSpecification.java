package com.proyect.disneyApi.service.specifications;

import com.proyect.disneyApi.dto.MovieDetailsDto;
import com.proyect.disneyApi.dto.MovieDto;
import com.proyect.disneyApi.model.Movie;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieSpecification {

    @Autowired
    MovieService movieService;

    @Autowired
    CharacterService characterService;

    public static Specification<Movie> movieFilter(MovieDetailsDto filter){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> criteriaList = new ArrayList<>();

            if(StringUtils.hasText(filter.getTitle())){
                criteriaList.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%"+filter.getTitle().toUpperCase(Locale.ROOT)+"%"));
            }

            if(StringUtils.hasText(filter.getYear())){
                criteriaList.add(criteriaBuilder.like(root.get("year").as(String.class), "%"+filter.getYear()+"%"));
            }

            if(StringUtils.hasText(filter.getGender())){
                criteriaList.add(criteriaBuilder.like(root.get("gender").as(String.class), filter.getGender()));
            }
            return criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()]));
        };
    }
}
