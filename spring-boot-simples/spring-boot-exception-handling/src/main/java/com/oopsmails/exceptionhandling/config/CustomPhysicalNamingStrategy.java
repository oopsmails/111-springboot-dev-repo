package com.oopsmails.exceptionhandling.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

//import org.hibernate.boot.model.naming.Identifier;
//import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
//import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
//import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final String SCHEMA_NAME = "SSS";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        if (name == null) {
            return null;
        }
        String newName = SCHEMA_NAME + "." + name.getText();
        return Identifier.toIdentifier(newName);
    }
}
