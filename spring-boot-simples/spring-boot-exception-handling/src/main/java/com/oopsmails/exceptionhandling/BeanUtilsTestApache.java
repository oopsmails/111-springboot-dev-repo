package com.oopsmails.exceptionhandling;

import com.oopsmails.exceptionhandling.model.DestinationBean;
import com.oopsmails.exceptionhandling.model.NestedBean;
import com.oopsmails.exceptionhandling.model.NestedBean2;
import com.oopsmails.exceptionhandling.model.NestedBeanLayer2;
import com.oopsmails.exceptionhandling.model.SourceBean;
import com.oopsmails.exceptionhandling.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/****
 *
 * Yes, BeanUtils.copyProperties from Apache Commons BeanUtils library copies properties between two beans,
 * but it only operates on the first level properties. It does not perform a deep copy of nested objects or properties.
 *
 * For example, if you have a source bean with a property that is another object (a second level property),
 * copyProperties will copy the reference to that object, not the properties of the nested object itself.
 * In other words, it performs a shallow copy.
 *
 * If you need to perform a deep copy, where nested objects are also copied recursively, you would need to implement
 * custom logic to handle this, possibly using reflection or other libraries that support deep copying.
 *
 */
@Slf4j
public class BeanUtilsTestApache {
    public static void main(String[] args) {
        SourceBean source = getSourceBeanTestObject();
        DestinationBean destination = new DestinationBean();

        try {
            BeanUtils.copyProperties(destination, source);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(destination.getName()); // Prints "John"
//        System.out.println(destination.getNested()); // Prints "NestedValue"
//        System.out.println(destination.getNested().getNestedBeanLayer2().getValueL2()); // Prints "nestedValueL2"

        JsonUtils.printJsonObject("source: ", source);
        JsonUtils.printJsonObject("destination: ", destination);
//        JsonUtils.printJsonObject(destination);
//        log.info("destination = [{}]", destination);

        // Test shallow copying
        source.getNested().getNestedBeanLayer2().setValueL2("nestedValueL2-changed");

        System.out.println(source.getNested().getNestedBeanLayer2().getValueL2()); // Prints "nestedValueL2-changed"
        System.out.println(destination.getNested().getNestedBeanLayer2().getValueL2()); // Prints "nestedValueL2-changed"

//        JsonUtils.printJsonObject(source);
//        JsonUtils.printJsonObject(destination);
    }

    public static SourceBean getSourceBeanTestObject() {
        SourceBean source = new SourceBean();
        source.setName("John");
        source.setEmail(null); // null String field
        source.setSourceName("sourceName");

        NestedBean nested = new NestedBean();
        nested.setValue("NestedValue");
        source.setNested(nested);

        NestedBeanLayer2 nestedBeanLayer2 = new NestedBeanLayer2();
        nestedBeanLayer2.setValueL2("nestedValueL2");
        nested.setNestedBeanLayer2(nestedBeanLayer2);

        NestedBean2 nestedBean2 = new NestedBean2();
        nestedBean2.setValue("secondNestedBean");
        source.setSecondNestedBean(nestedBean2);

        return source;
    }
}
