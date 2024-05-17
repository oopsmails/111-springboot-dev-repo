package com.oopsmails.exceptionhandling;

import com.oopsmails.exceptionhandling.model.DestinationBean;
import com.oopsmails.exceptionhandling.model.NestedBean;
import com.oopsmails.exceptionhandling.model.NestedBeanLayer2;
import com.oopsmails.exceptionhandling.model.SourceBean;
import com.oopsmails.exceptionhandling.util.BeanUtilsOopsmails1;
import com.oopsmails.exceptionhandling.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BeanUtilsTestOopsmails1 {
    public static void main(String[] args) {
        SourceBean source = BeanUtilsTestApache.getSourceBeanTestObject();
        DestinationBean destination = new DestinationBean();

        BeanUtilsOopsmails1.copyProperties(source, destination);
//        BeanUtilsOopsmails1.copyProperties(source, destination, "secondNestedBean");

        JsonUtils.printJsonObject("source: ", source);
        JsonUtils.printJsonObject("destination: ", destination);
    }

    public static <T> void mergeIgnoreNullProperties(final T source, final T target, String... ignoreProperties) {
        ArrayUtils.addAll(ignoreProperties, BeanUtilsTestOopsmails1.getNullPropertyNames(source));
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static String[] mergeStringArrays(String[] array1, String[] array2) {
        return ArrayUtils.addAll(array1, array2);
    }
    public static String[] getNullPropertyNames(final Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        final Set<String> emptyNames = new HashSet<String>();
        for (final java.beans.PropertyDescriptor pd : pds) {
            final Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        final String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
