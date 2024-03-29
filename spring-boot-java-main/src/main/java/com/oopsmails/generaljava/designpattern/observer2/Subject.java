package com.oopsmails.generaljava.designpattern.observer2;

public interface Subject {
	public void registerObserver(Observer observer);

	public void removeObserver(Observer observer);

	public void notifyObservers();
}
