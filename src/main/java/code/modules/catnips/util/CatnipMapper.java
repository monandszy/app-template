package code.modules.catnips.util;

import code.configuration.SpringMapperConfig;
import code.modules.catnips.CatnipCommandFacade.CatnipCreateDto;
import code.modules.catnips.CatnipQueryFacade.CatnipReadDto;
import code.modules.catnips.data.CatnipEntity;
import code.modules.catnips.service.Catnip;
import code.util.Generated;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

@Mapper(config = SpringMapperConfig.class)
@AnnotateWith(Generated.class)
public interface CatnipMapper {

  CatnipEntity domainToEntity(Catnip domain);

  Catnip entityToDomain(CatnipEntity entity);

  CatnipReadDto domainToReadDto(Catnip domain);

  Catnip createDtoToDomain(CatnipCreateDto createDto);
}