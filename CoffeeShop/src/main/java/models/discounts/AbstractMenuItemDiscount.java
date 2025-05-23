package models.discounts;

import java.util.Objects;

import models.IMenuItem;

public abstract class AbstractMenuItemDiscount implements IMenuItemDiscount{

	protected IMenuItem menuItem;
	protected boolean sumWithOthers;
	private String promocode;

	public AbstractMenuItemDiscount(IMenuItem menuItem, boolean sumWithOthers) {
		this.menuItem = menuItem;
		this.sumWithOthers = sumWithOthers;
	}

	@Override
	public boolean sumWithOthers() {
		return sumWithOthers;
	}

	@Override
	public boolean check(Object object) {
		return Objects.equals(object, menuItem);
	}

	@Override
	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}
	
	@Override
	public String getPromocode()
	{
		return promocode;
	}
}
