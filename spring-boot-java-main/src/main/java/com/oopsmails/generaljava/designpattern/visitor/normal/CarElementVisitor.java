package com.oopsmails.generaljava.designpattern.visitor.normal;

public interface CarElementVisitor {
	void visit(Wheel wheel);
    void visit(Engine engine);
    void visit(Body body);
    void visit(Car car);
}
