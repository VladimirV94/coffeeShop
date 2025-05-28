package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import model.Cafe;

public class CafeApp {

	private static final String MENU_COMMAND = "menu";
	private static final String ORDER_COMMAND = "order";
	private static final String EXIT = "exit";
	private static final String PAY_ORDER = "pay";

	private static final String COMMANDS_LIST = new StringBuilder("Commands\n")
		.append(ORDER_COMMAND).append(" - show order\n")
		.append(MENU_COMMAND).append(" - show menu\n")
		.append("add *menu item name* count *number* - add menu item to order\n")
		.append("promocode *promocode name* - select discount promocode\n")
		.append(PAY_ORDER).append(" - pay order\n")
		.append(EXIT).append(" - exit from app\n")
		.toString();

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

		while (true) {
			try {
				String command = getEnteredCommand();

				switch (Command.fromString(command)) {
				case MENU_COMMAND: {
					showMenu();
					break;
				}
				case ORDER_COMMAND: {
					showOrder();
					break;
				}
				case EXIT: {
					showMessage("app work finished");
					break;
				}
				case PAY_ORDER: {
					createTextСheck();
					break;
				}
				case MENU_ITEM: {
					String menuItemName = Command.getMenuItemNameFromString(command);
					int menuItemCount = Command.getMenuItemCountFromString(command);
					addMenuItemsToOrder(menuItemName, menuItemCount);
					break;
				}
				case PROMOCODE: {
					String promocode = Command.getPromocodeFromString(command);
					updatePromocode(promocode);
					break;
				}
				default:
					throw new IllegalArgumentException("Unknown command:" + command);
				}
			} catch (Exception e) {
				e.printStackTrace();
				showCommandsList();
			}
		}
	}

	/**
	 * Вывести в консоль набор доступных команд
	 */
	private void showCommandsList() {
		showMessage(COMMANDS_LIST);
	}

	/**
	 * Добавить в заказ указанную позицию и её кеоличество
	 */
	private void addMenuItemsToOrder(String itemName, int count) {
		cafe.addMenuItemToOrder(orderId, itemName, count);
	}

	/**
	 * Обновить введённый промокод
	 * 
	 * @param promocode
	 */
	private void updatePromocode(String promocode) {
		if (cafe.checkPromocode(promocode)) {
			showMessage("promocode applied");
			this.promocode = promocode;
		} else {
			this.promocode = null;
			throw new NoSuchElementException("Uncorrect promocode" + promocode);
		}
	}

	private void createTextСheck() {
		// TODO создание текстового файла
		showMessage("order paid");
	}

	/**
	 * Показать меню кофейни
	 */
	private void showMenu() {
		showMessage(cafe.getMenu().toString());
	}

	/**
	 * Показать заказ
	 */
	private void showOrder() {
		// Показываем выбранные позиции и их количество
		cafe.getMenuItemsByOrder(orderId).forEach((menuItem, count) -> {
			showMessage(new StringBuilder().append("Item: ").append(menuItem.getName()).append(". Count: ")
					.append(count).toString());
		});

		// Показываем общую стоймость заказа

		// Если был введён корректный промокод, то показываем стоймость с его учётом
		if (promocode != null) {
			showMessage("Promocode: " + promocode);
			showMessage("Total price: " + cafe.getOrderTotalPrice(orderId, promocode));
		} else
			// Иначе показываем стоймость только с учётом скидок за категории
			showMessage("Total price: " + cafe.getOrderTotalPrice(orderId));
	}

	private void showMessage(String message) {
		System.out.println(message);
	}

	private String getEnteredCommand() throws IOException {
		return br.readLine();
	}
}
