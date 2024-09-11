package code.modules.pot.internal;

import code.events.CatnipCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventService {

//  @Async
//  @Transactional(propagation = Propagation.REQUIRES_NEW)
//  @TransactionalEventListener
//  @EventListener
  @ApplicationModuleListener
  void on(CatnipCreatedEvent event)  {
    System.out.println("CATT");
    log.info("event recived!: {}", event.id());
    log.info("event recivedd!: {}", event.id());
    log.info("event reciveddd!: {}", event.id());
  }
}