package apps.interfaces;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import models.Basket;
import models.Cafe;
import models.IMenuItem;
import models.Menu.Category;

public class CustomerSessionInterface {

	private Cafe cafe;

	private MainMenuInterface mainMenuInterface;
	private MenuInterface menuInterface;
	private OrderInterface orderInterface;
	private CategoryInterface categoryInterface ;
	private MenuItemInterface menuItemInterface;
	private BasketInterface basketInterface;

	// TODO По хорошему нужно как-то вынести логику привязки IMenutem к Category из меню в отдельный класс 
	// и использовать его для меню и корзины
	// Так же разделить логику корзины как таковой от условно абстрактного набора <Category, <IMenutem, Count>>, 
	// который может быть как набран пользователем, так и иметь другое происхождение
	private Basket basket;
	
	public CustomerSessionInterface(Cafe cafe) {
		this.cafe = cafe;
		basket = new Basket(cafe.getMenu());
		mainMenuInterface = new MainMenuInterface(this);
		menuInterface = new MenuInterface(this);
		orderInterface = new OrderInterface(this);
		categoryInterface = new CategoryInterface(this);
		menuItemInterface = new MenuItemInterface(this);
		basketInterface = new BasketInterface(this);
	}

	public void startSession()
	{
		mainMenuInterface.show();
	}

	int showMainMenuInterface()
	{
		return mainMenuInterface.show();
	}

	int showMenuInterface()
	{
		return menuInterface.show();
	}

	int showOrderInterface()
	{
		return orderInterface.show();
	}

	int showCategoryInterface(Category category)
	{
		return categoryInterface.show(category);
	}

	int showMenuItemInterface(IMenuItem menuItem)
	{
		return menuItemInterface.show(menuItem);
	}

	int showBasketInterface()
	{
		return basketInterface.show();
	}

	Cafe getCafe()
	{
		return cafe;
	}

	public BigDecimal getOriginalPrice(IMenuItem menuItem, int count) {
		return cafe.getOriginalPrice(menuItem, count);
	}

	public BigDecimal getDiscountPrice(IMenuItem menuItem, int count) {
		return cafe.getDiscountPrice(menuItem, count);
	}

	public Map<IMenuItem, BigDecimal> getDiscountPrice(Basket basket, String promocode) {
		return promocode != null && promocode.isEmpty() ? null : cafe.getDiscountPrice(basket, promocode);
	}

	void addInBasket(IMenuItem menuItem, int count) {
		
		// TODO отработка отрицательных чисел. Либо запрет и реализация removeFromBasket(IMenuItem menuItem, int count)
		// либо разрешение и отнимание тут.
		// Так же проверка, что нельзя иметь отрицательное количество заказанных позиций. 
		// TODO При нуле заказанных позиций убираем из корзины
		// TODO Реализация добавления в корзину ещё какого-то количества позиции,
		// а не затирание старого результата
		basket.put(menuItem, count);
	}

	void removeFromBasket(IMenuItem menuItem) {
		
		basket.remove(menuItem);
	}

	void clearBasket()
	{
		basket.clear();
	}

	Basket getBasket()
	{
		return basket;
	}
}