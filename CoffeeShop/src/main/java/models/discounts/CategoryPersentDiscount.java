package models.discounts;

import java.math.BigDecimal;

import models.Menu.Category;

public class CategoryPersentDiscount extends AbstractCategoryDiscount implements IPercentDiscount{

	private BigDecimal persent;
	private PercentDiscountDelegate persentDelegate;

	public CategoryPersentDiscount(Category menuItemCategory, BigDecimal persent, boolean sumWithOthers) {
		super(menuItemCategory, sumWithOthers);
		persentDelegate = new PercentDiscountDelegate();
		this.persent = persent;
	}

	@Override
	public BigDecimal persent() {
		return persent;
	}

	@Override
	public BigDecimal calculatePrice(BigDecimal price) {
		return persentDelegate.calculatePrice(price, persent);
	}
}