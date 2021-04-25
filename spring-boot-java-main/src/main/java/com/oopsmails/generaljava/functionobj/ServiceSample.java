package com.oopsmails.generaljava.functionobj;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ServiceSample {

    // try using custom defined Command
    private Map<Class, Command> commandMap;

    // try using generic java8 Function
    private Map<Class, Function> functionMap;

    private DaoSample daoSample;

    public ServiceSample(DaoSample daoSample) {
        this.daoSample = daoSample;
        initialThis();
    }

    /**
     * This is with String as key, also working. But using Class is better.
     *
		public void initialThis() {
			final Map<String, Command> tempCommandMap = new HashMap<>();
			tempCommandMap.put("childC", (ParentC parentC) -> {
				if (parentC instanceof ChildC) {
					return daoSample.getString((ChildC) parentC);
				}
				return null;
			});
			tempCommandMap.put("parentC", parentC -> daoSample.getString(parentC));
			commandMap = Collections.unmodifiableMap(tempCommandMap);
		}

		public String getStringFromCommand(String commandType, ParentC parentC) {
			Command command = commandMap.get(commandType);
			if (command == null) {
				throw new IllegalArgumentException("Invalid command type: "
					+ commandType);
			}

			return command.getStringCommand(parentC);
		}
    */

    public void initialThis() {
        final Map<Class, Command> tempCommandMap = new HashMap<>();
        tempCommandMap.put(ChildC.class, childC -> daoSample.getString((ChildC) childC));
        tempCommandMap.put(ParentC.class, parentC -> daoSample.getString(parentC));
        commandMap = Collections.unmodifiableMap(tempCommandMap);

        final Map<Class, Function<ParentC, String>> tempFunctionMap = new HashMap<>();
        tempFunctionMap.put(ChildC.class, childC -> daoSample.getString((ChildC) childC));
        tempFunctionMap.put(ParentC.class, parentC -> daoSample.getString(parentC));
        functionMap = Collections.unmodifiableMap(tempFunctionMap);
    }

    public String getStringFromCommand(Class classKey, ParentC parentC) {
        Command command = commandMap.get(classKey);
        if (command == null) {
            throw new IllegalArgumentException("Invalid command type: "
                    + classKey);
        }

        return command.getStringCommand(parentC);
    }

    public String getStringFromFunction(Class classKey, ParentC parentC) {
        Function<ParentC, String> function = functionMap.get(classKey);
        if (function == null) {
            throw new IllegalArgumentException("Invalid function type: "
                    + classKey);
        }

        return function.apply(parentC);
    }

}
