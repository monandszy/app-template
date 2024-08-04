package code.modules.catnip.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Catnip {

  UUID id;

}