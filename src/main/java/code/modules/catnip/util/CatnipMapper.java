package code.modules.catnip.util;

import code.modules.catnip.CatnipReadDto;
import code.modules.catnip.data.CatnipEntity;
import code.modules.catnip.service.Catnip;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@AnnotateWith(value = code.config.Generated.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatnipMapper {
  
  CatnipEntity modelToEntity(Catnip catnip);

  Catnip entityToModel(CatnipEntity catnipEntity);

  CatnipReadDto modelToReadDto(Catnip catnip);
}