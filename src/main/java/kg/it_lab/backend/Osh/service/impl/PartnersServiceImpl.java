package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.partner.PartnersResponse;
import kg.it_lab.backend.Osh.mapper.PartnerMapper;
import kg.it_lab.backend.Osh.repository.PartnersRepository;
import kg.it_lab.backend.Osh.service.PartnersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnersServiceImpl implements PartnersService {

    private final PartnerMapper partnerMapper;
    private final PartnersRepository partnersRepository;

    @Override
    public PartnersResponse all() {
        return partnerMapper.toDtoS(partnersRepository.findAll());
    }
}
