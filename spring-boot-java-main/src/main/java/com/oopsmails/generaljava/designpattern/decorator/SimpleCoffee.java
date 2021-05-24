package com.oopsmails.generaljava.designpattern.decorator;

//implementation of a simple coffee without any extra ingredients
public class SimpleCoffee implements Coffee {
	public double getCost() {
		return 1;
	}

	public String getIngredients() {
		return "Coffee";
	}
}
