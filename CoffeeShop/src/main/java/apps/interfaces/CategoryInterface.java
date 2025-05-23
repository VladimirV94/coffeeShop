package apps.interfaces;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import models.Cafe;
import models.IMenuItem;
import models.Menu.Category;

public class CategoryInterface extends AbstacratInterface{

	private CustomerSessionInterface customerSessionInterface;
	private Map<Integer, IMenuItem> menuItems;

	public CategoryInterface(CustomerSessionInterface customerSessionInterface)
	{
		this.customerSessionInterface = customerSessionInterface;
	}

	protected int show(Category category)
	{
		AtomicInteger menuItemIndex = new AtomicInteger(FIRST_MENUITEM_COMMAND_ID);
		menuItems = customerSessionInterface.getCafe().getMenu().getItems(category).stream()
				.collect(Collectors.toMap(menuItem -> menuItemIndex.getAndIncrement(), menuItem -> menuItem));
		
		showItemsMenuByCategoryOptions(category);
		
		while(customerSessionInterface.getCafe().isOpen())
		{
			int selectedCommandId = getSelectedCommandId();
			if(selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID)
				return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
			
			if(selectedCommandId == BASKET_COMMAND_ID)
			{
				return customerSessionInterface.showBasketInterface();
			}
			
			selectedCommandId = customerSessionInterface.showMenuItemInterface(menuItems.get(selectedCommandId));
			
			if(selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID)
			{
				showItemsMenuByCategoryOptions(category);
			}
			if(selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID)
			{
				return RETURN_TO_MAINMENU_COMMAND_ID;
			}
		}
		return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
	}

	private void showItemsMenuByCategoryOptions(Category category) 
	{
		customerSessionInterface.getCafe().getMenu().getItems(category).forEach(menuItemByCategory ->
		{
			sendMessage(getKeyByValue(menuItems, menuItemByCategory) + ": " + menuItemByCategory.getName());
		});
		sendMessage(BASKET_COMMAND_ID +": go to basket");
		sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID +": return to categories");
	}

	private <T, E> T getKeyByValue(Map<T, E> map, E value) {
		return map.entrySet().stream()
			.filter(entry -> entry.getValue() == value)
			.map(Map.Entry::getKey)
			.findFirst().orElse(null);
	}
}
