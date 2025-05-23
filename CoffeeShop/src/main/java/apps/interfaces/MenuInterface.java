package apps.interfaces;

public class MenuInterface extends AbstacratInterface{

	private CustomerSessionInterface customerSessionInterface;

	public MenuInterface(CustomerSessionInterface customerSessionInterface) {
		this.customerSessionInterface = customerSessionInterface;
	}

	protected int show() {

		showMenuOptions();
		
		while(customerSessionInterface.getCafe().isOpen())
		{
			switch(getSelectedCommandId())
			{
				case ORDERMENU_COMMAND_ID:
				{
					int selectedCommandId = customerSessionInterface.showOrderInterface();
					if(selectedCommandId == RETURN_TO_PREVIOUS_MENU_COMMAND_ID)
					{
						showMenuOptions();
					}
					if(selectedCommandId == RETURN_TO_MAINMENU_COMMAND_ID)
					{
						return RETURN_TO_MAINMENU_COMMAND_ID;
					}
					break;
				}
				case RETURN_TO_PREVIOUS_MENU_COMMAND_ID:
				{
					return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
				}
			}
		}
		
		return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
	}

	private void showMenuOptions() {
		sendMessage();
		sendMessage("Welcome to Coffee Shop's menu");
		customerSessionInterface.getCafe().getMenu().getItems().forEach((category, menuItems) ->
		{
			sendMessage(category);
			menuItems.forEach(menuItem -> sendMessage("	" + menuItem));
		});
		sendMessage();
		sendMessage(ORDERMENU_COMMAND_ID + ": make an order");
		sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID +": return to main menu");
	}

}
