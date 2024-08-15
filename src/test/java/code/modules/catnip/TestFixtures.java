package code.modules.catnip;

import code.modules.catnip.service.Catnip;
import code.web.catnip.CatnipCreateDto;
import lombok.experimental.UtilityClass;

@UtilityClass
class TestFixtures {

  Catnip catnip = Catnip.builder().build();

  CatnipCreateDto catnipCreateDto = new CatnipCreateDto();
}