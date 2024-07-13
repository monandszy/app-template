package code.modules.catnip.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CatnipRepo implements CatnipDao {

  private CatnipJpaRepo catnipJpaRepo;
}