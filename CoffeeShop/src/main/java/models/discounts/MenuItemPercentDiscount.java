package models.discounts;

import java.math.BigDecimal;

import models.IMenuItem;

public class MenuItemPercentDiscount extends AbstractMenuItemDiscount implements IPercentDiscount {

	private BigDecimal percent;
	private PercentDiscountDelegate persentDelegate;

	public MenuItemPercentDiscount(IMenuItem menuItem, BigDecimal percent, boolean sumWithOthers) {
		super(menuItem, sumWithOthers);
		persentDelegate = new PercentDiscountDelegate();
		this.percent = percent;
	}

	@Override
	public BigDecimal persent() {
		return percent;
	}

	@Override
	public BigDecimal getDiscountPrice() {
		return persentDelegate.calculatePrice(menuItem.getPrice(), percent);
	}
}
