package models.discounts;

import java.math.BigDecimal;

public class PercentDiscountDelegate{

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	BigDecimal calculatePrice(BigDecimal price, BigDecimal persentDiscount)
	{
		return price.subtract(persentDiscount.divide(ONE_HUNDRED).multiply(price));
	}

}
