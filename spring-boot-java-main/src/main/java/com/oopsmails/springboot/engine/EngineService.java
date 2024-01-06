package com.oopsmails.springboot.engine;

import com.oopsmails.model.ITask;
import com.oopsmails.model.TaskBeanNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EngineService {
    final private Set<TaskBeanNode> allWorkNodes = new HashSet<>();
    final private Set<Object> beanSet = new HashSet<>();

    private List<TaskBeanNode> rootNodes = new ArrayList<>();

    private String workflowPackage = "com.oopsmails.springboot.service";

    @Autowired
    private ApplicationContext applicationContext;

    public ITask getStartTask() {
        return rootNodes.get(0).getTask();
    }

    public Set<TaskBeanNode> getAllNodes() {
        return allWorkNodes;
    }

    private List<String> getTokens(String path) {
        String s = path.substring(workflowPackage.length());
        return Arrays.stream(s.split("\\."))
                .map(e -> e.trim())
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        log.info("Initializing engine...");
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(
                e -> {
                    Object bean = applicationContext.getBean(e);
                    String path = bean.getClass().getName();
                    if (path.startsWith(workflowPackage)) {
                        beanSet.add(bean);
                    }
                }
        );

        beanSet.stream().forEach(e -> {
            log.info("in beanSet: {}", e);
        });
    }

//    @PostConstruct
//    public void init() {
//        log.info("Initializing engine...");
//        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(
//                e -> {
//                    Object bean = applicationContext.getBean(e);
//                    String path = bean.getClass().getName();
//                    if (path.startsWith(workflowPackage) && bean instanceof ITask) {
//                        List<String> tokens = getTokens(path);
//                        TaskBeanNode parentNode = null;
//                        for (int i = 0; i < tokens.size(); i++) {
//                            TaskBeanNode node = new TaskBeanNode(tokens.get(i), i == tokens.size() - 1 ? (ITask) bean : null, parentNode);
//                            allWorkNodes.add(node);
//                            TaskBeanNode existingNode = allWorkNodes.stream().filter(n -> n.equals(node)).findFirst().get(); // Not Null
//                            if (i == tokens.size() - 1 && existingNode.getParentNode() != null) {
//                                existingNode.getParentNode().getChildren().add(node);
//                            }
//                            parentNode = existingNode;
//                        }
//                    }
//                }
//        );
//
//        allWorkNodes.stream().forEach(e -> {
//            e.sortChildren();
//        });
//
//        rootNodes = allWorkNodes.stream().filter(e -> e.getParentNode() == null).sorted(comparator).collect(Collectors.toList());
//
//        if (rootNodes.isEmpty() || rootNodes.get(0).isFolder()) {
//            throw new IllegalStateException("No workflow has been defined!");
//        }
//
//        allWorkNodes.stream()
//                .map(e -> e.getBean())
//                .filter(e -> e != null)
//                .map(e -> e.getName())
//                .sorted()
//                .forEach(e -> {
//                    log.info("Task loaded: {}", e);
//                });
//
//        log.info("initialized.");
//    }
}
