package model.meal;

import java.math.BigDecimal;

import model.MenuItemCategory;

public interface IMenuItem {

	String getName();

	BigDecimal getPrice();
	
	/**
	 * Получить цену со скидкой, если она есть. 
	 * Иначе получить полную стоймость
	 * @return
	 */
	BigDecimal getDiscountPrice();
	
	MenuItemCategory getCategory();
}
