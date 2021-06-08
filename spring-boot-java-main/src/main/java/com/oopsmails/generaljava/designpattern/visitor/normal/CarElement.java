package com.oopsmails.generaljava.designpattern.visitor.normal;

public interface CarElement {
	void accept(CarElementVisitor visitor); // CarElements have to provide accept().
}
