package model.meal;

import java.math.BigDecimal;

import model.MenuItemCategory;

public interface IMenuItem {

	String getName();

	BigDecimal getPrice();
	
	BigDecimal getDiscountPrice();
	
	MenuItemCategory getCategory();
}
