package java_knights.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.*;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricRegistry;

@ApplicationScoped
@Health
public class HelloHealthCheck implements HealthCheck {

    @Inject
    MetricRegistry metricRegistry;

    @Override
    public HealthCheckResponse call() {
        long hello_count = metricRegistry.getCounters((n, m) -> n.endsWith("hello counter"))
                                         .values()
                                         .stream()
                                         .map(Counter::getCount)
                                         .reduce(0L, Long::sum);

        return HealthCheckResponse.builder()
                                  .state(hello_count > 0)
                                  .name("hello health")
                                  .withData("total hello counter", hello_count)
                                  .build();
    }
}
