package code.configuration;

import static io.opentelemetry.sdk.resources.Resource.create;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.resources.Resource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

  private static final AttributeKey<String> SERVICE_NAME = AttributeKey.stringKey("service.name");
  private static final Resource MANDATORY = create(Attributes.of(SERVICE_NAME, "unknown_service:java"));

  // TODO remove export bloat

//  @Bean
//  OpenTelemetry openTelemetry(  ) {
//    Resource resource = MANDATORY;
//    SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
//      .setResource(resource)
//      .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build())
//      .setSampler(Sampler.parentBased(Sampler.traceIdRatioBased(0.1)))
//      .build();
//
//
//    SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
//      .setResource(resource)
//      .registerMetricReader(PeriodicMetricReader.builder(OtlpGrpcMetricExporter.builder().build()).build())
//      .build();
//
//    return OpenTelemetrySdk.builder()
//      .setTracerProvider(sdkTracerProvider)
//      .setMeterProvider(sdkMeterProvider)
//      .buildAndRegisterGlobal();
//  }
}