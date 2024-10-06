package code.util;

import static code.modules.catnips.CatnipQueryFacade.CatnipReadDto;

import code.modules.catnips.CatnipCommandFacade.CatnipCreateDto;
import code.modules.catnips.service.Catnip;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestFixtures {

  public Catnip catnip = Catnip.builder().build();

  public CatnipReadDto catnipReadDto = new CatnipReadDto(UUID.randomUUID());

  public CatnipCreateDto catnipCreateDto = new CatnipCreateDto();
}