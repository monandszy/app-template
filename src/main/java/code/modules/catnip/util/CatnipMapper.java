package code.modules.catnip.util;

import code.configuration.Generated;
import code.configuration.SpringMapperConfig;
import code.modules.catnip.CatnipCreateDto;
import code.modules.catnip.CatnipReadDto;
import code.modules.catnip.data.CatnipEntity;
import code.modules.catnip.service.Catnip;
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