package models.discounts;

import java.math.BigDecimal;

public interface ICategoryDiscount extends IDiscount{

	BigDecimal calculatePrice(BigDecimal menuItemPrice);
}
