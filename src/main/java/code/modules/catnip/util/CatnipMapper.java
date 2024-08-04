package code.modules.catnip.util;

import code.modules.catnip.CatnipCreateDto;
import code.modules.catnip.CatnipReadDto;
import code.modules.catnip.data.CatnipEntity;
import code.modules.catnip.service.Catnip;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@AnnotateWith(value = code.configuration.Generated.class)
public interface CatnipMapper {

  CatnipEntity domainToEntity(Catnip catnip);

  Catnip entityToDomain(CatnipEntity catnipEntity);

  CatnipReadDto domainToReadDto(Catnip catnip);

  Catnip createDtoToDomain(CatnipCreateDto catnipCreateDto);
}