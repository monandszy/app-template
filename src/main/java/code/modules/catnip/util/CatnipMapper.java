package code.modules.catnip.util;

import code.configuration.SpringMapperConfig;
import code.modules.catnip.data.CatnipEntity;
import code.modules.catnip.service.Catnip;
import code.util.Generated;
import code.web.catnip.CatnipCreateDto;
import code.web.catnip.CatnipReadDto;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

@Mapper(config = SpringMapperConfig.class)
@AnnotateWith(Generated.class)
public interface CatnipMapper {

  CatnipEntity domainToEntity(Catnip catnip);

  Catnip entityToDomain(CatnipEntity catnipEntity);

  CatnipReadDto domainToReadDto(Catnip catnip);

  Catnip createDtoToDomain(CatnipCreateDto catnipCreateDto);
}