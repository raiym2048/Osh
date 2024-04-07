package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.partner.PartnersResponse;
import kg.it_lab.backend.Osh.entities.Partners;

import java.util.List;

public interface PartnerMapper {
    PartnersResponse toDtoS(List<Partners> partners);
}
