package com.proyect.disneyApi.service.specifications;

import com.proyect.disneyApi.dto.CharacterDetailsDto;
import com.proyect.disneyApi.dto.CharacterDto;
import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CharacterSpecification {

    @Autowired
    MovieService movieService;

    @Autowired
    CharacterService characterService;

    public static Specification<Character> characterFilter(CharacterDetailsDto filter){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> criteriaList = new ArrayList<>();

            if (StringUtils.hasText(filter.getName())){
                criteriaList.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%"+filter.getName().toUpperCase(Locale.ROOT)+"%"));
            }


            if (filter.getAge() != null){
                criteriaList.add(criteriaBuilder.equal(root.get("age"), "%"+filter.getAge()+"%"));
            }

            if (filter.getWeight() != null){
                criteriaList.add(criteriaBuilder.like(root.get("weight"), "%"+filter.getWeight()+"%"));
            }

            return criteriaBuilder.and(criteriaList.toArray(new Predicate[criteriaList.size()]));
        };
    }
}
