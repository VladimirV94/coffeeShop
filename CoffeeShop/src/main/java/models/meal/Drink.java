package models.meal;

import models.AbstractMenuItem;

public class Drink extends AbstractMenuItem {

	private double volume;

	public Drink(String name, double price, double volume) {
		super(name, price);
		this.volume = volume;
	}

	public double getVolume() {
		return volume;
	}
	
	@Override
	public String toString() {
		
		return new StringBuilder(getName()).append(". Price:")
			.append(getPrice()).append(". Volume:").append(getVolume()).toString();
	}
}
