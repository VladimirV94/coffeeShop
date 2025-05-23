package services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import models.Basket;
import models.IMenuItem;
import models.Menu.Category;
import models.discounts.ICategoryDiscount;
import models.discounts.IDiscount;
import models.discounts.IMenuItemDiscount;

public class DiscountService {

	private Collection<IDiscount> discounts;

	public void setDiscounts(Collection<IDiscount> discounts)
	{
		this.discounts = discounts;
	}

	public BigDecimal calculatePrice(Category itemCategory, IMenuItem menuItem, int count) {
		
		BigDecimal bigDecimalMenuItemCount = new BigDecimal(count);
		BigDecimal fullOriginalPrice = menuItem.getPrice().multiply(bigDecimalMenuItemCount);
		
		ICategoryDiscount categoryDiscount = findDiscount(itemCategory).map(ICategoryDiscount.class::cast).orElse(null);
		IMenuItemDiscount menuItemDiscount = findDiscount(menuItem).map(IMenuItemDiscount.class::cast).orElse(null);
		
		// Если есть скидка как на позицию, так и на категорию и они сумируются
		if(categoryDiscount != null && menuItemDiscount != null &&
		   categoryDiscount.sumWithOthers() && menuItemDiscount.sumWithOthers())
		{
			// то сначала получаем цену с учётом скидки на позицию,и умножаем на количество позиций. 
			BigDecimal fullPriceWithDiscount = menuItemDiscount.getDiscountPrice()
					.multiply(bigDecimalMenuItemCount);
			
			// а после отнимаем скидку за категорию. 
			// Скидка за категорию вычисляется с учётом уже применённой скидки за позицию
			return fullPriceWithDiscount
				.subtract(categoryDiscount.calculatePrice(fullPriceWithDiscount));
		}
		// Если есть только одна скидка или скидки не сумируются, то приоритетной скидкой считаем скидку на позицию
		if(menuItemDiscount != null)
		{
			return menuItemDiscount.getDiscountPrice()
					.multiply(bigDecimalMenuItemCount);
		}
		// Иначе смотрим, есть ли скидка на категорию
		if(categoryDiscount != null)
		{
			return categoryDiscount.calculatePrice(fullOriginalPrice);
		}
		// Если не было найдено скидок
		return fullOriginalPrice;
	}

	public Map<IMenuItem, BigDecimal> calculatePrice(Basket basket, String promocode) {
		
		IDiscount discount = findDiscount(promocode).orElse(null);
		if(discount == null)
			return null;
		
		Map<IMenuItem, BigDecimal> promocodePrices = new LinkedHashMap<>();
		if(discount.sumWithOthers())
		{
			basket.getItems().forEach((item, count) ->
			{
				Category category = basket.getCategory(item);
				if(discount.check(basket.getCategory(item)))
				{
					BigDecimal discountPrice = calculatePrice(category, item, count);
					promocodePrices.put(item, ((ICategoryDiscount) discount).calculatePrice(discountPrice));
				}
				else
				if(discount.check(item))
				{
					BigDecimal fullPrice = item.getPrice();
					BigDecimal fullDiscount = item.getPrice().multiply(new BigDecimal(2));
					fullDiscount.subtract(calculatePrice(category, item, count));
					fullDiscount.subtract(((IMenuItemDiscount) discount).getDiscountPrice());
					promocodePrices.put(item, fullPrice.subtract(fullDiscount));
				}
				else
				{
					promocodePrices.put(item, calculatePrice(category, item, count));
				}
			});
			return promocodePrices;
		}
		else
		{
			//TODO Обработка промокода, если он не суммируется с остальными скидками
		}
		return null;
	}
	
	/**
	 * Найти скидку спрятанную под указанным промокодом
	 * @param object
	 * @return
	 */
	private Optional<IDiscount> findDiscount(String promocode) {
		return discounts.stream()
			.filter(discount -> Objects.equals(discount.getPromocode(), promocode))
			.findFirst();
	}
	
	/**
	 * Найти скидку для указанного объекта, которая не спрятана под промокодом
	 * @param object
	 * @return
	 */
	private Optional<IDiscount> findDiscount(Object object) {
		return discounts.stream()
			.filter(discount -> discount.getPromocode() == null && discount.check(object))
			.findFirst();
	}
}