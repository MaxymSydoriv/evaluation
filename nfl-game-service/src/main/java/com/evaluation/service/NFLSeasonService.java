package com.evaluation.service;

import com.evaluation.entity.Season;
import com.evaluation.exception.RestException;
import com.evaluation.model.SeasonDTO;
import com.evaluation.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NFLSeasonService implements DtoCrudService<Season, SeasonDTO, Integer> {

    private final SeasonRepository seasonRepository;

    @Override
    public SeasonDTO getById(Integer id) {
        return seasonRepository.findById(id).map(this::toSeasonDTO).orElseThrow(() -> new RestException("Season not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public SeasonDTO save(SeasonDTO seasonDTO) {
        Season season = new Season();
        season.setName(seasonDTO.getName());
        season.setStartDate(seasonDTO.getStartDate());
        season.setEndDate(seasonDTO.getEndDate());
        seasonRepository.save(season);
        seasonDTO.setId(seasonDTO.getId());
        return seasonDTO;
    }

    private SeasonDTO toSeasonDTO(Season season) {
        SeasonDTO seasonDTO = new SeasonDTO();
        seasonDTO.setId(season.getId());
        seasonDTO.setName(season.getName());
        seasonDTO.setStartDate(season.getStartDate());
        seasonDTO.setEndDate(season.getEndDate());
        return seasonDTO;
    }
}
