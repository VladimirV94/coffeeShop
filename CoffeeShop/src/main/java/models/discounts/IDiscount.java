package models.discounts;

public interface IDiscount {

	boolean sumWithOthers();

	boolean check(Object object);

	void setPromocode(String promocode);

	String getPromocode();
}
