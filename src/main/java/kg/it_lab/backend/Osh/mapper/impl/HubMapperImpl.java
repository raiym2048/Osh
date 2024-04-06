package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.hub.HubRequest;
import kg.it_lab.backend.Osh.dto.hub.HubResponse;
import kg.it_lab.backend.Osh.entities.Hub;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.mapper.HubMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HubMapperImpl implements HubMapper {
    @Override
    public HubResponse toDto(Hub hub) {
        HubResponse hubResponse = new HubResponse();
        hubResponse.setId(hub.getId());
        hubResponse.setName(hub.getName());
        hubResponse.setDescription(hub.getDescription());
        hubResponse.setSubtopic(hub.getSubtopic());
        ArrayList<String> paths = new ArrayList<>();
        for(Image image: hub.getImages()){
            paths.add(image.getPath());
        }
        hubResponse.setImagePaths(paths);
        return hubResponse;
    }

    @Override
    public List<HubResponse> toDtoS(List<Hub> hubs) {
        List<HubResponse> hubResponses = new ArrayList<>();
        for(Hub hub : hubs){
            hubResponses.add(toDto(hub));
        }
        return hubResponses;
    }

    @Override
    public Hub toDtoHub(Hub hub, HubRequest hubRequest) {
        hub.setName(hubRequest.getName());
        hub.setDescription(hubRequest.getDescription());
        hub.setSubtopic(hubRequest.getSubtopic());
        return hub;
    }
}
