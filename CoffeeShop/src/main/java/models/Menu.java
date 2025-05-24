package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class Menu {
	public enum Category {
		FOOD("Food"), DRINK("Drink"), DESSERTS("Desserts"), OTHER("other");

		private String name;

		Category(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private Map<Category, List<IMenuItem>> items = new LinkedHashMap<>();

	public HashMap<Category, List<IMenuItem>> getItems() {
		return new LinkedHashMap<>(items);
	}

	public Collection<IMenuItem> getItems(Category category) {
		return new ArrayList<>(items.get(category));
	}

	public Category getCategory(IMenuItem menuItem) {
		for (Entry<Category, List<IMenuItem>> itemsByCategory : items.entrySet()) {
			if (itemsByCategory.getValue().contains(menuItem)) {
				return itemsByCategory.getKey();
			}
		}
		return null;
	}

	public void addItem(Category itemCategory, IMenuItem item) {
		Optional.ofNullable(items.get(itemCategory)).ifPresentOrElse(itemsByCategory -> itemsByCategory.add(item),
				() -> {
					List<IMenuItem> itemsByCategory = new ArrayList<>();
					itemsByCategory.add(item);
					items.put(itemCategory, itemsByCategory);
				});
	}

	public void removeItem(Category itemCategory, IMenuItem item) {
		Optional.ofNullable(items.get(itemCategory)).ifPresent(items -> items.remove(item));
	}
}