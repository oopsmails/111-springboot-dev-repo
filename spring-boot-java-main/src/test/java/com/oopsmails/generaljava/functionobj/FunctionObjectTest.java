package com.oopsmails.generaljava.functionobj;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oopsmails on 2018-01-20.
 * <p>
 * Test class, using Function object to get rid of big switch!
 * <p>
 * Basically, using a Map, key is the real object class, value is Function object.
 * Then, can invoke Function dynamically based on passed in object class type.
 * In this way, will NOT pollute pojo with dao.
 */

public class FunctionObjectTest {

    @Test
    public void testFunctions() throws Exception {
        DaoSample daoSample = new DaoSample();
        ServiceSample serviceSample = new ServiceSample(daoSample);
        ParentC parentC1 = new ChildC("parentC1: actual ChildC", "cid");
        System.out.println("Class: " + parentC1.getClass());
        ParentC parentC2 = new ParentC("parentC2: actual ParentC");

        List<String> results = new ArrayList<>();
//		results.add(serviceSample.getStringFromCommand("childC", parentC1));
//		results.add(serviceSample.getStringFromCommand("parentC", parentC2));
//		results.add(serviceSample.getStringFromCommand("parentC", parentC1)); // cannot(no neat way to) do magic cast

        results.add(serviceSample.getStringFromCommand(parentC1.getClass(), parentC1)); // three is magic case in commandMap
        results.add(serviceSample.getStringFromCommand(parentC2.getClass(), parentC2));

        results.add(serviceSample.getStringFromFunction(parentC1.getClass(), parentC1)); // three is magic case in commandMap
        results.add(serviceSample.getStringFromFunction(parentC2.getClass(), parentC2));

        for (int i = 0; i < results.size(); i++) {
            System.out.println("" + i + ": " + results.get(i));
        }

        assertNotNull(daoSample);
    }

}
