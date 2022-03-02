package com.proyect.disneyApi.service.implementations;

import com.proyect.disneyApi.model.Gender;
import com.proyect.disneyApi.repository.GenderRepository;
import com.proyect.disneyApi.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public Gender saveGender(Gender gender) {
        return genderRepository.save(gender);
    }

    @Override
    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    @Override
    public Gender getGenderByName(String genderName) {
        return genderRepository.findGenderByName(genderName);
    }

    @Override
    public Optional<Gender> getGenreById(Long id) {
        return genderRepository.findById(id);
    }

    @Override
    public void deleteGender(Gender gender) {
        genderRepository.delete(gender);
    }
}
