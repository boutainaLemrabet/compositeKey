package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.WithIdStringDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WithIdString} and its DTO {@link WithIdStringDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WithIdStringMapper extends EntityMapper<WithIdStringDTO, WithIdString> {
    @Mapping(target = "withIdStringDetails", ignore = true)
    WithIdString toEntity(WithIdStringDTO withIdStringDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WithIdStringDTO toDtoId(WithIdString withIdString);
}
