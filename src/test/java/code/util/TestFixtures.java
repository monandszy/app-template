package code.util;

import code.modules.catnip.service.Catnip;
import code.web.catnip.CatnipCreateDto;
import code.web.catnip.CatnipReadDto;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestFixtures {

  public Catnip catnip = Catnip.builder().build();

  public CatnipReadDto catnipReadDto = new CatnipReadDto(UUID.randomUUID());

  public CatnipCreateDto catnipCreateDto = new CatnipCreateDto();
}