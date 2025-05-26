package model.meal;

import model.MenuItemCategory;

public class Drink extends AbstractMenuItem {

	private double volume;

	public Drink(String name, double price, double volume, MenuItemCategory category) {
		super(name, price, category);
		this.volume = volume;
	}

	public double getVolume() {
		return volume;
	}

	@Override
	public String toString() {

		return new StringBuilder(getName())
			.append(". Price:").append(getPrice())
			.append(". Volume:").append(getVolume()).toString();
	}
}
