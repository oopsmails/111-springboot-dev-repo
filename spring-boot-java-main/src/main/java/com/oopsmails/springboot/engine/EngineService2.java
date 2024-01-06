package com.oopsmails.springboot.engine;

import com.oopsmails.model.ITask;
import com.oopsmails.model.TaskBeanNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EngineService2 implements ApplicationListener<ContextRefreshedEvent> {
    final private Set<TaskBeanNode> allTaskBeanNodes = new HashSet<>();
    final private Set<Object> beanSet = new HashSet<>();

    private final List<TaskBeanNode> rootNodes = new ArrayList<>();

    private final String servicePackage = "com.oopsmails.springboot.service";

    @Autowired
    private ApplicationContext applicationContext;

    public ITask getStartTask() {
        return rootNodes.get(0).getTask();
    }

    public Set<TaskBeanNode> getAllNodes() {
        return allTaskBeanNodes;
    }

    private List<String> getTokens(String path) {
        String s = path.substring(servicePackage.length());
        return Arrays.stream(s.split("\\."))
                .map(e -> e.trim())
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Use ApplicationListener:
     * Instead of using @PostConstruct, you can implement ApplicationListener<ContextRefreshedEvent> to perform
     * initialization after the application context has been completely refreshed.
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().equals(applicationContext)) {
            initializeAfterConstruction();
        }
    }

    private void initializeAfterConstruction() {
        log.info("Initializing engine...");
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(
                e -> {
                    Object bean = applicationContext.getBean(e);
                    String path = bean.getClass().getName();
                    if (path.startsWith(servicePackage)) {
                        beanSet.add(bean);
                    }
                }
        );

        beanSet.stream().forEach(e -> {
            log.info("in beanSet: {}", e);
        });
    }
}
