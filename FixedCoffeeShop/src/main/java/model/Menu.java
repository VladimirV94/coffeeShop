package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.AttributeNotFoundException;

import model.meal.IMenuItem;

public class Menu {

	private Map<MenuItemCategory, List<IMenuItem>> groupedItems;
	private List<IMenuItem> items;

	public Menu(List<IMenuItem> items) {
		this.items = items;
		groupedItems = items.stream().collect(Collectors.groupingBy(item -> item.getCategory()));
	}

	public Optional<IMenuItem> getItem(String name) {
		return items.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findFirst();
	}
	
	public List<IMenuItem> getItems() {
		return new ArrayList<>(items);
	}

	public Collection<IMenuItem> getItems(MenuItemCategory category) {
		return new ArrayList<>(groupedItems.get(category));
	}

	@Override
	public String toString() {
		
		StringBuilder menu = new StringBuilder();
		groupedItems.forEach((category, menuItems) ->
		{
			menu.append(category);
			
			BigDecimal categoryDiscount = category.getDiscount();
			if(categoryDiscount != null)
			{
				menu.append(". Discount: ").append(categoryDiscount).append("%");
				menu.append("\n");
				menuItems.forEach(menuItem -> menu
					.append(menuItem).append(". Discount price ").append(menuItem.getDiscountPrice())
					.append("\n"));
			}
			else
			{
				menu.append("\n");
				menuItems.forEach(menuItem -> menu
					.append(menuItem)
					.append("\n"));
			}
		});
		return menu.toString();
	}
}