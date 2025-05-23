package models.discounts;

import java.math.BigDecimal;

public interface IFixCountDiscount extends IDiscount{
	
	BigDecimal fixCount();
}
