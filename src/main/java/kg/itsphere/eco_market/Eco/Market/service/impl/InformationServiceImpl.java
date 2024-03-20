package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.info.Information;

import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.InformationRepository;
import kg.itsphere.eco_market.Eco.Market.service.InformationService;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.InformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;
    private final InformationMapper informationMapper;
    @Override
    public void createInfo(InformationRequest informationRequest) {
        Information information = new Information();
        informationRepository.save(informationMapper.toDtoInformation(information, informationRequest));
    }

    @Override
    public InformationResponse findById(Long id) {
        Optional<Information> information = informationRepository.findById(id);
        checker(information, id);
        return informationMapper.toDto(information.get());
    }

    @Override
    public void updateById(Long id, InformationRequest informationRequest) {
        Optional<Information> information = informationRepository.findById(id);
        checker(information, id);
        informationRepository.save(informationMapper.toDtoInformation(information.get(), informationRequest));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Information> information = informationRepository.findById(id);
        checker(information, id);
        informationRepository.deleteById(id);
    }

    private void checker(Optional<Information> information, Long id) {
        if(information.isEmpty()) {
            throw new NotFoundException("Information with id \"" + id + "\" not foun", HttpStatus.NOT_FOUND);
        }
    }
}
