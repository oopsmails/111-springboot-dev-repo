package com.oopsmails.kafka.health;

import com.oopsmails.kafka.avro.util.OopsmailsKafkaAdminClientBean;
import com.oopsmails.kafka.avro.util.OopsmailsKafkaSchemaRegistryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty("oopsmails.kafka.enabled")
public class OopsmailsKafkaHealthIndicator implements HealthIndicator {
    @Autowired
    private OopsmailsKafkaAdminClientBean oopsmailsKafkaAdminClientBean;

    @Autowired
    private OopsmailsKafkaSchemaRegistryBean oopsmailsKafkaSchemaRegistryBean;

    @Value(value = "${oopsmails.kafka.health.timeout.ms:5000}")
    private int healthTimeout;

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();

        oopsmailsKafkaAdminClientBean.runOopsmailsKafkaHealthyCheck
                (count -> {
                    log.info("Oopsmails Kafka is up in monitoring.");
                    builder.withDetail("Oopsmails Kafka Brokers", "UP");
                    builder.withDetail("activeNodeCount", count);
                }, (count, th) -> {
                    log.error("Oopsmails Kafka is down in monitoring.");
                    builder.withDetail("Oopsmails Kafka Brokers", "DOWN");
                    builder.withDetail("activeNodeCount", count);
                    if (th != null) {
                        builder.withException(th);
                    }
                });

        oopsmailsKafkaSchemaRegistryBean.runOopsmailsKafkaSchemaRegistryHealthyCheck(
                () -> {
                    log.info("Oopsmails Kafka Schema Registry is up in monitoring.");
                    builder.withDetail("Oopsmails Kafka Schema Registry", "UP");
                }, th -> {
                    log.error("Oopsmails Kafka Schema Registry is down in monitoring.");
                    builder.withDetail("Oopsmails Kafka Schema Registry", "DOWN");
                    if (th != null) {
                        builder.withException(th);
                    }
                });

        builder.up();
        return builder.build();
    }
}
