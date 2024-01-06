package com.oopsmails.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskBeanNode {
    final public static Comparator<TaskBeanNode> comparator = new Comparator<TaskBeanNode>() {
        @Override
        public int compare(TaskBeanNode n1, TaskBeanNode n2) {
            int rtn = n1.seq - n2.seq;
            if (rtn == 0) {
                rtn = n1.token.compareTo(n2.token);
            }
            return rtn;
        }
    };

    private final String token;

    private final int seq;

    private final ITask bean;

    private final List<TaskBeanNode> children = new ArrayList<>();

    private final TaskBeanNode parentNode;

    public TaskBeanNode(String token, ITask bean, TaskBeanNode parentNode) {
        this.token = token;
        this.bean = bean;
        this.parentNode = parentNode;

        String extracted = extractDigits(token);
        if (extracted.isEmpty()) {
            throw new IllegalStateException("No <Sequence> identified for " + token);
        }

        this.seq = Integer.parseInt(extracted);
    }

    private static String extractDigits(String input) {
        Matcher matcher = Pattern.compile("\\d+").matcher(input);
        return matcher.find() ? matcher.group() : "";
    }

    @Override
    public String toString() {
        return token;
    }

    public void sortChildren() {
        children.sort(comparator);
    }

    public boolean isFolder() {
        return bean == null;
    }

    public ITask getTask() {
        return bean;
    }

    public boolean matchBeanName(String beanName) {
        return bean != null && StringUtils.equalsIgnoreCase(bean.getName(), beanName);
    }

    @Override
    public int hashCode() {
        return token == null ? 0 : token.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (that != null && that instanceof TaskBeanNode) {
            TaskBeanNode thatNode = (TaskBeanNode) that;
            if (parentNode != null && !parentNode.equals(thatNode.parentNode)) {
                return false;
            } else if (parentNode == null && thatNode.parentNode != null) {
                return false;
            }
            return StringUtils.equalsIgnoreCase(token, thatNode.token);
        }
        return false;
    }

    public String getToken() {
        return token;
    }

    public int getSeq() {
        return seq;
    }

    public TaskBeanNode getParentNode() {
        return parentNode;
    }

    public ITask getBean() {
        return bean;
    }

    public List<TaskBeanNode> getChildren() {
        return children;
    }
}
