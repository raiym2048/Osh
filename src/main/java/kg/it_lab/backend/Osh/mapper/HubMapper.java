package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.hub.HubRequest;
import kg.it_lab.backend.Osh.dto.hub.HubResponse;
import kg.it_lab.backend.Osh.entities.Hub;
import kg.it_lab.backend.Osh.entities.Image;

import java.util.List;

public interface HubMapper {
    HubResponse toDto(Hub hub);
    List<HubResponse> toDtoS(List<Hub> hubs);
    Hub toDtoHub(Hub hub , HubRequest hubRequest);
}
