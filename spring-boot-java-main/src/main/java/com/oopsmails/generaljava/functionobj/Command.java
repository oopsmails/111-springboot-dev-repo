package com.oopsmails.generaljava.functionobj;



/**
 *  another normal way ...
 *
 *  NOTE: Or if you really need to pass arguments (because they represent the work state and not the algorithm state),
 *  then you should just do so and call it "strategy pattern" instead.

public interface Command {
    void execute();
}

public class ConcreteCommand implements Command {

    private Object something;

    public ConcreteCommand(Object something) {
        this.something = something;
    }

    @Override
    public void execute() {
        // ...
    }

}
*/

public interface Command {
    String getStringCommand(ParentC parentC);
}
