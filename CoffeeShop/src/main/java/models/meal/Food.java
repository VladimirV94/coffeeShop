package models.meal;

import models.AbstractMenuItem;

public class Food extends AbstractMenuItem {

	private double calories;

	public Food(String name, double price, double calories) {
		super(name, price);
		this.calories = calories;
	}

	public double getCalories() {
		return calories;
	}

	@Override
	public String toString() {

		return new StringBuilder(getName())
			.append(". Price:").append(getPrice())
			.append(". Calories:").append(getCalories()).toString();
	}
}
