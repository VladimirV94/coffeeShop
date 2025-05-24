package apps.interfaces;

import models.IMenuItem;

public class MenuItemInterface extends AbstacratInterface {

	private CustomerSessionInterface customerSessionInterface;

	public MenuItemInterface(CustomerSessionInterface customerSessionInterface) {
		this.customerSessionInterface = customerSessionInterface;
	}

	protected int show(IMenuItem menuItem) {
		showMenuItemOptions(menuItem);

		int selectedCommandIdOrCountMenuItem = getSelectedCommandId();
		// Если введена команда возврата
		if (selectedCommandIdOrCountMenuItem == RETURN_TO_PREVIOUS_MENU_COMMAND_ID)
			return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;

		// Иначе введено количетво позиций. Кладём их в корзину
		customerSessionInterface.addInBasket(menuItem, selectedCommandIdOrCountMenuItem);

		showMenuItemOptionsAfterAdditional();

		switch (getSelectedCommandId()) {
		case BASKET_COMMAND_ID: {
			int selectedCommandId = customerSessionInterface.showBasketInterface();
			if (selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID) {
				return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
			}
			if (selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID) {
				return RETURN_TO_MAINMENU_COMMAND_ID;
			}
		}
		case RETURN_TO_PREVIOUS_MENU_COMMAND_ID: {
			return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
		}
		}

		return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
	}

	private void showMenuItemOptions(IMenuItem menuItem) {
		sendMessage(menuItem);
		sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID + ": return to category");
		sendMessage("count to add to order: ");
	}

	private void showMenuItemOptionsAfterAdditional() {
		sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID + ": continue order");
		sendMessage(BASKET_COMMAND_ID + ": basket menu");
	}
}
