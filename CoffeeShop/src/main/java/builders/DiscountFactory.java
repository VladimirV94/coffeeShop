package builders;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import models.IMenuItem;
import models.Menu.Category;
import models.discounts.CategoryFixCountDiscount;
import models.discounts.CategoryPersentDiscount;
import models.discounts.IDiscount;
import models.discounts.MenuItemFixCountDiscount;
import models.discounts.MenuItemPercentDiscount;

public class DiscountFactory {
	
	private Set<IDiscount> discounts = new HashSet<>();
	private String promocode;

	public DiscountFactory createPercentDiscount(Category menuItemCategory, double percent, boolean sumWithOthers)
	{
		setDiscount(new CategoryPersentDiscount(menuItemCategory, new BigDecimal(percent), sumWithOthers));
		return this;
	}

	public DiscountFactory createPercentDiscount(IMenuItem iMenuItem, double percent, boolean sumWithOthers)
	{
		setDiscount(new MenuItemPercentDiscount(iMenuItem, new BigDecimal(percent), sumWithOthers));
		return this;
	}

	public DiscountFactory createFixCountDiscount(Category menuItemCategory, double fixCount, boolean sumWithOthers)
	{
		setDiscount(new CategoryFixCountDiscount(menuItemCategory, new BigDecimal(fixCount), sumWithOthers));
		return this;
	}

	public DiscountFactory createFixCountDiscount(IMenuItem iMenuItem, double fixCount, boolean sumWithOthers)
	{
		setDiscount(new MenuItemFixCountDiscount(iMenuItem, new BigDecimal(fixCount), sumWithOthers));
		return this;
	}

	public Collection<IDiscount> getDiscounts()
	{
		return discounts;
	}
	
	private void setDiscount(IDiscount discount) {
		
		Optional.ofNullable(promocode).ifPresent(discount::setPromocode);
		discounts.add(discount);
	}

	public DiscountFactory setPromoCode(String promocode) {
		this.promocode = promocode;
		return this;
	}
}
