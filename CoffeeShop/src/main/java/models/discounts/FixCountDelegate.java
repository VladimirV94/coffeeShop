package models.discounts;

import java.math.BigDecimal;

public class FixCountDelegate {

	BigDecimal calculatePrice(BigDecimal price, BigDecimal fixCountDiscount) {
		return price.subtract(fixCountDiscount);
	}

}
