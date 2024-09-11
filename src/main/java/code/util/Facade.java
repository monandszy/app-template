package code.util;

import jakarta.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Validated
@Transactional
public @interface Facade {
}