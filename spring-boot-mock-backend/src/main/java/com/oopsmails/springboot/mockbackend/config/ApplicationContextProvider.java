package com.oopsmails.springboot.mockbackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationContextProvider implements ApplicationContextAware {

    @Value("${logging.from.project.name:backend-mock}")
    private String loggingFromProjectName;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
        log.info("loggingFromProjectName: [{}]", this.loggingFromProjectName);
    }

    public ApplicationContext getContext() {
        return applicationContext;
    }


}
