package models.discounts;

import java.math.BigDecimal;

public interface IMenuItemDiscount extends IDiscount {

	BigDecimal getDiscountPrice();
}
