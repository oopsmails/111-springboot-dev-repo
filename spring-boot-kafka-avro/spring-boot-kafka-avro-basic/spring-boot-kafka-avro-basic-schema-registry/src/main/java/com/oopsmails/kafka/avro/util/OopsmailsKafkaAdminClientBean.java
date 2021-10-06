package com.oopsmails.kafka.avro.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
@Slf4j
public class OopsmailsKafkaAdminClientBean {
    @Autowired
    private Properties oopsmailsKafkaConsumerFactoryProps;

    @Value(value = "${oopsmails.kafka.health.timeout.ms:5000}")
    private int healthTimeout;

    public void runOopsmailsKafkaHealthyCheck(Consumer<Integer> onHealthy, BiConsumer<Integer, Throwable> onNotHealthy) {
        runUsingKafkaAdminClient(adminClient -> {
            try {
                DescribeClusterResult describeClusterResult = adminClient.describeCluster(new DescribeClusterOptions().timeoutMs(healthTimeout));
                describeClusterResult.clusterId().get();

                if (describeClusterResult != null) {
                    KafkaFuture<Collection<Node>> nodesFuture = describeClusterResult.nodes();
                    if (nodesFuture != null) {
                        Collection<Node> nodes = nodesFuture.get();
                        if (nodes != null) {
                            int count = nodes.size();
                            log.info("Live nodes of brokers: {}", count);

                            if (count > 0) {
                                onHealthy.accept(count);
                                return;
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
                onNotHealthy.accept(0, throwable);
            }

        });
    }

    private void runUsingKafkaAdminClient(Consumer<AdminClient> consumer) {
        AdminClient adminClient = null;
        try {
            log.debug("oopsmailsKafkaConsumerFactoryProps.get({}): [{}]", ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, oopsmailsKafkaConsumerFactoryProps.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
            adminClient = AdminClient.create(oopsmailsKafkaConsumerFactoryProps);
            consumer.accept(adminClient);
        } finally {
            close(adminClient);
        }
    }

    private void close(AdminClient adminClient) {
        if (adminClient != null) {
            try {
                close(adminClient);
            } catch (Throwable throwable) {
                log.warn(throwable.getMessage(), throwable); // Exception will not propagate
            }
        }
    }
}
