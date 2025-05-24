package models.discounts;

import java.math.BigDecimal;

import models.Menu.Category;

public class CategoryFixCountDiscount extends AbstractCategoryDiscount implements IFixCountDiscount {

	private BigDecimal fixCount;
	private FixCountDelegate fixCountDelegate;

	public CategoryFixCountDiscount(Category menuItemCategory, BigDecimal fixCount, boolean sumWithOthers) {
		super(menuItemCategory, sumWithOthers);
		fixCountDelegate = new FixCountDelegate();
		this.fixCount = fixCount;
	}

	@Override
	public BigDecimal fixCount() {
		return fixCount;
	}

	@Override
	public BigDecimal calculatePrice(BigDecimal price) {
		return fixCountDelegate.calculatePrice(price, fixCount);
	}
}