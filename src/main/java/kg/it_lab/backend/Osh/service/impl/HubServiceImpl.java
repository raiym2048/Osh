package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.hub.HubResponse;
import kg.it_lab.backend.Osh.mapper.HubMapper;
import kg.it_lab.backend.Osh.repository.HubRepository;
import kg.it_lab.backend.Osh.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final HubMapper hubMapper;
    private final HubRepository hubRepository;

    @Override
    public List<HubResponse> all() {
        return hubMapper.toDtoS(hubRepository.findAll());
    }
}
