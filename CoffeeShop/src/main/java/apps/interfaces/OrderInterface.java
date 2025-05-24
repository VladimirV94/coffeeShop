package apps.interfaces;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import models.Menu.Category;

public class OrderInterface extends AbstacratInterface {

	private CustomerSessionInterface customerSessionInterface;
	private Map<Integer, Category> categories;

	public OrderInterface(CustomerSessionInterface customerSessionInterface) {
		this.customerSessionInterface = customerSessionInterface;

		AtomicInteger categoryIndex = new AtomicInteger(FIRST_CATEGORY_COMMAND_ID);
		categories = customerSessionInterface.getCafe().getMenu().getItems().keySet().stream().collect(Collectors
				.toMap(menuItemCategory -> categoryIndex.getAndIncrement(), menuItemCategory -> menuItemCategory));
	}

	protected int show() {

		showOrderMenuOptions();
		while (customerSessionInterface.getCafe().isOpen()) {
			int selectedCommandId = getSelectedCommandId();
			if (selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID)
				return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;

			if (selectedCommandId == BASKET_COMMAND_ID) {
				selectedCommandId = customerSessionInterface.showBasketInterface();
				if (selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID) {
					showOrderMenuOptions();
				} else if (selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID) {
					return RETURN_TO_MAINMENU_COMMAND_ID;
				}
			} else {
				selectedCommandId = customerSessionInterface.showCategoryInterface(categories.get(selectedCommandId));
				if (selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID) {
					showOrderMenuOptions();
				} else if (selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID) {
					return RETURN_TO_MAINMENU_COMMAND_ID;
				}
			}
		}
		return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
	}

	private void showOrderMenuOptions() {
		sendMessage("Welcome to order maker");
		categories.forEach((commandId, category) -> {
			sendMessage(commandId + ":" + category);
		});
		sendMessage(BASKET_COMMAND_ID + ": go to basket");
		sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID + ": return to previous menu");
	}

}
