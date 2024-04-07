package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.partner.PartnersResponse;
import kg.it_lab.backend.Osh.entities.Partners;
import kg.it_lab.backend.Osh.mapper.PartnerMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartnerMapperImpl implements PartnerMapper {

    @Override
    public PartnersResponse toDtoS(List<Partners> partners) {
        PartnersResponse response = new PartnersResponse();
        List<String> images = new ArrayList<>();
        for(Partners partner: partners){
            if (partner.getImage() != null)images.add(partner.getImage().getPath());
        }
        response.setImagePaths(images);
        return  response;
    }
}
