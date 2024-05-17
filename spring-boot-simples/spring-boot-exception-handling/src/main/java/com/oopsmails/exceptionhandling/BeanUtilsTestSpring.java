package com.oopsmails.exceptionhandling;

import com.oopsmails.exceptionhandling.model.DestinationBean;
import com.oopsmails.exceptionhandling.model.SourceBean;
import com.oopsmails.exceptionhandling.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class BeanUtilsTestSpring {
    public static void main(String[] args) {
        SourceBean source = BeanUtilsTestApache.getSourceBeanTestObject();
        DestinationBean destination = new DestinationBean();

        BeanUtils.copyProperties(source, destination);
//        BeanUtils.copyProperties(source, destination, "secondNestedBean");

        JsonUtils.printJsonObject("source: ", source);
        JsonUtils.printJsonObject("destination: ", destination);
    }

}
