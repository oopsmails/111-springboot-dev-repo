package com.oopsmails.generaljava.functionobj;

public class DaoSample {
    public String getString(ParentC parentC) {
        String retVal = parentC.acctNum;
        System.out.println("DaoSample, in getString: ParentC " + retVal);
        return retVal;
    }

    public String getString(ChildC childC) {
        String retVal = childC.acctNum;
        System.out.println("DaoSample, in getString: ChildC " + retVal);
        return retVal;
    }
}
