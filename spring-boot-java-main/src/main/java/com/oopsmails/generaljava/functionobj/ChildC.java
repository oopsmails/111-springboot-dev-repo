package com.oopsmails.generaljava.functionobj;

public class ChildC extends ParentC {
    public String clientId;

    public ChildC(String acctNum, String clientId) {
        super(acctNum);
        this.clientId = clientId;
    }
}
