package com.oopsmails.generaljava.functionobj;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *  ref: https://stackoverflow.com/questions/55170208/how-to-pass-methods-as-parameters
 */

public class FunctionAsParameter {

    public static Object handleInputs(Function<Object,Object> method, List<Object> inputs) {
        return method.apply(inputs.get(0));
    }

    public static Object handleInputs(BiFunction<Object,Object,Object> method, List<Object> inputs) {
        return method.apply(inputs.get(0), inputs.get(1));
    }

    public static void main(String args[]) {
        MyClass mc = new MyClass();

        String str = (String)handleInputs((a) -> mc.myToString((String)a), Arrays.asList("string"));
        System.out.println(str); // string

        Integer sum = (Integer)handleInputs((a) -> mc.mySum((int)a), Arrays.asList(1));
        System.out.println(sum); // 1

        Integer sum2 = (Integer)handleInputs((a,b) -> mc.mySum((int)a, (int)b), Arrays.asList(1, 2));
        System.out.println(sum2); // 3
    }

    static class MyClass {
        public Object myToString(String a) {
            return new String(a);
        }

        public Object myToString(String a, String b) {
            return new String(a + ", " + b);
        }

        public Object mySum(int a) {
            return Integer.valueOf(a);
        }

        public Object mySum(int a, int b) {
            return Integer.valueOf(a + b);
        }
    }
}
