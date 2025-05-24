package models.discounts;

import java.util.Objects;

import models.Menu.Category;

public abstract class AbstractCategoryDiscount implements ICategoryDiscount {

	protected Category category;
	protected boolean sumWithOthers;
	private String promocode;

	public AbstractCategoryDiscount(Category category, boolean sumWithOthers) {
		this.category = category;
		this.sumWithOthers = sumWithOthers;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public boolean sumWithOthers() {
		return sumWithOthers;
	}

	@Override
	public boolean check(Object object) {
		return Objects.equals(object, category);
	}

	@Override
	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	@Override
	public String getPromocode() {
		return promocode;
	}
}
