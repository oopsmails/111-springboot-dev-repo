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

import static com.oopsmails.model.TaskBeanNode.comparator;

@Component
@Slf4j
public class EngineService {
    final private Set<TaskBeanNode> allTaskBeanNodes = new HashSet<>();
    final private Set<Object> beanSet = new HashSet<>();

    private List<TaskBeanNode> rootNodes = new ArrayList<>();

    private String servicePackage = "com.oopsmails.springboot.service";

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

    @PostConstruct
    public void init() {
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

//    @PostConstruct
//    public void init() {
//        log.info("Initializing engine...");
//        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(
//                e -> {
//                    Object bean = applicationContext.getBean(e);
//                    String path = bean.getClass().getName();
//                    if (path.startsWith(servicePackage) && bean instanceof ITask) {
//                        List<String> tokens = getTokens(path);
//                        TaskBeanNode parentNode = null;
//                        for (int i = 0; i < tokens.size(); i++) {
//                            TaskBeanNode node = new TaskBeanNode(tokens.get(i), i == tokens.size() - 1 ? (ITask) bean : null, parentNode);
//                            allTaskBeanNodes.add(node);
//                            TaskBeanNode existingNode = allTaskBeanNodes.stream().filter(n -> n.equals(node)).findFirst().get(); // Not Null
//                            if (i == tokens.size() - 1 && existingNode.getParentNode() != null) {
//                                existingNode.getParentNode().getChildren().add(node);
//                            }
//                            parentNode = existingNode;
//                        }
//                    }
//                }
//        );
//
//        allTaskBeanNodes.stream().forEach(e -> {
//            e.sortChildren();
//        });
//
//        rootNodes = allTaskBeanNodes.stream().filter(e -> e.getParentNode() == null).sorted(comparator).collect(Collectors.toList());
//
//        if (rootNodes.isEmpty() || rootNodes.get(0).isFolder()) {
//            throw new IllegalStateException("No taskNode has been defined!");
//        }
//
//        allTaskBeanNodes.stream()
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
