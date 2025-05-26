package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.AttributeNotFoundException;

import model.Cafe;

public class CafeApp{
	
	private static final String MENU_COMMAND = "menu";
	private static final String ORDER_COMMAND = "order";
	private static final String EXIT = "exit";
	private static final String PAY_ORDER = "pay";
	
	private static final Pattern MENU_ITEM_PATTERN = Pattern.compile("^add\\s+(.+?)\\s+count\\s+(\\d+)$", Pattern.CASE_INSENSITIVE);
	private static final Pattern PROMOCODE_PATTERM = Pattern.compile("^promocode\\s+(\\w+)$", Pattern.CASE_INSENSITIVE);

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private Cafe cafe;
	private int orderId;
	private String promocode;

	public CafeApp(Cafe cafe) {
		this.cafe = cafe;
		orderId = cafe.createOrder();
	}

	public void start() {
		
		showCommandsList();
		
		while(true) 
		{
			String command = null;
			try {
				command = getEnteredCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Matcher menuItemMatcher = MENU_ITEM_PATTERN.matcher(command);
			Matcher promocodeMatcher = PROMOCODE_PATTERM.matcher(command);
			if(menuItemMatcher.matches())
			{
				addMenuItemsToOrder(menuItemMatcher);
			}
			else
			if(promocodeMatcher.matches())
			{
				updatePromocode(promocodeMatcher);
			}
			else
			if(command.equalsIgnoreCase(MENU_COMMAND)) {
				showMenu();
			}
			else
			if(command.equalsIgnoreCase(ORDER_COMMAND)) {
				showOrder();			
			}
			else
			if(command.equalsIgnoreCase(EXIT)) {
				showMessage("app work finished");
				break;
			}
			else
			if(command.equalsIgnoreCase(PAY_ORDER)) {
				createTextСheck();
			}
			else
			{
				showMessage("Unknown command");
				showCommandsList();
			}
		}
	}

	private void showCommandsList() {
		showMessage("Commands");
		showMessage("add *menu item name* count *number*");
		showMessage("promocode *promocode name*");
		showMessage(ORDER_COMMAND + "- show order");
		showMessage(MENU_COMMAND + "- show menu");
		showMessage(PAY_ORDER + "- exit from app");
		showMessage(EXIT + " - exit from app");
	}

	private void addMenuItemsToOrder(Matcher menuItemMatcher) {
		String itemName = menuItemMatcher.group(1);
        int count = Integer.parseInt(menuItemMatcher.group(2));
        cafe.addMenuItemToOrder(orderId, itemName, count);
	}

	private void updatePromocode(Matcher promocodeMatcher) {
		String promocode = promocodeMatcher.group(1);
		if(cafe.checkPromocode(promocode))
		{	showMessage("promocode applied");
			this.promocode = promocode;
		}
		else
		{	
			showMessage("uncorrect promocode");
			this.promocode = promocode;
		}
	}

	private void createTextСheck() {
		//TODO создание текстового файла
		showMessage("order paid");
	}

	private void showMenu() {
		showMessage(cafe.getMenu().toString());
	}

	private void showOrder() {
		try {
			cafe.getMenuItemsByOrder(orderId).forEach((menuItem, count) ->
			{
				showMessage(new StringBuilder()
					.append("Item: ").append(menuItem.getName())
					.append(". Count: ").append(count).toString());
			});
			if(promocode != null)
			{
				showMessage("Promocode: " + promocode);
				showMessage("Total price: " + cafe.getOrderTotalPrice(orderId, promocode));
			}
			else
				showMessage("Total price: " + cafe.getOrderTotalPrice(orderId));
		} catch (AttributeNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void showMessage(String message) {
		System.out.println(message);
	}

	private String getEnteredCommand() throws IOException {
		return br.readLine();
	}
}
