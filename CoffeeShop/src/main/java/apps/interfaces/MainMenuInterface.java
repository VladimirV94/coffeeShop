package apps.interfaces;

public class MainMenuInterface extends AbstacratInterface {

	private CustomerSessionInterface customerSessionInterface;

	public MainMenuInterface(CustomerSessionInterface customerSessionInterface) {
		this.customerSessionInterface = customerSessionInterface;
	}

	public int show() {
		showMainMenuOptions();

		// XXX Вместо while и getSelectedCommandId() можно реализовать через слушателя
		// команд.
		// CustomerSessionInterface работает со Scanner и отправляет команды
		// Все интерфейсы слушают это событие
		// Сложность в фильтрации. Это требует либо уникальных id для каждой команды,
		// чтобы неактивные интефейсы не реагировали на комманды, которые не
		// предназначены им.
		// Либо необходимо оборачивать каждый интерфейс статусом активный/не активный и
		// скипать любые команды, если он не активен

		finished: while (customerSessionInterface.getCafe().isOpen()) {
			switch (getSelectedCommandId()) {
			case MENU_COMMAND_ID: {
				int selectedCommandId = customerSessionInterface.showMenuInterface();
				if (selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID
						|| selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID) {
					showMainMenuOptions();
				}
				break;
			}
			case ORDERMENU_COMMAND_ID: {
				int selectedCommandId = customerSessionInterface.showOrderInterface();
				if (selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID
						|| selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID) {
					showMainMenuOptions();
				}
				break;
			}
			case RETURN_TO_PREVIOUS_MENU_COMMAND_ID: {
				break finished;
			}
			}
		}
		return 0;
	}

	private void showMainMenuOptions() {

		sendMessage();
		sendMessage("Welcome to Coffee Shop");
		sendMessage(MENU_COMMAND_ID + ": show menu");
		sendMessage(ORDERMENU_COMMAND_ID + ": make an order");
		sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID + ": exit");
	}
}
