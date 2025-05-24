package apps.interfaces;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import models.IMenuItem;

public class BasketInterface extends AbstacratInterface {

	private CustomerSessionInterface customerSessionInterface;
	private Map<IMenuItem, Integer> basketItems;
	private String enteredPromocode;
	private Map<IMenuItem, BigDecimal> menuItemsAfterPromocode;

	public BasketInterface(CustomerSessionInterface customerSessionInterface) {
		this.customerSessionInterface = customerSessionInterface;
	}

	protected int show() {

		basketItems = customerSessionInterface.getBasket().getItems();

		showBasketMenuOptions();

		while (customerSessionInterface.getCafe().isOpen()) {
			switch (getSelectedCommandId()) {
			case RETURN_TO_PREVIOUS_MENU_COMMAND_ID: {
				return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
			}
			case CANCEL_ORDER_COMMAND_ID: {
				removeOrder();
				return RETURN_TO_MAINMENU_COMMAND_ID;
			}
			case ENTER_PROMOCODE_ID: {
				enterPromoCode();
				break;
			}
			case PAY_COMMAND_ID: {
				payOrdered();
				return RETURN_TO_MAINMENU_COMMAND_ID;
			}
			}
		}
		return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
	}

	private void showBasketMenuOptions() {

		if (enteredPromocode != null && !enteredPromocode.isEmpty()) {
			// TODO использовать StringBuilder вместо +
			BigDecimal totalOriginalPrice = new BigDecimal(0);
			BigDecimal totalPriceAfterPromocode = new BigDecimal(0);
			for (Entry<IMenuItem, Integer> entry : basketItems.entrySet()) {
				IMenuItem menuItem = entry.getKey();
				Integer count = entry.getValue();

				BigDecimal originalPrice = customerSessionInterface.getOriginalPrice(menuItem, count);
				BigDecimal priceAfterPromocode = menuItemsAfterPromocode.get(menuItem);

				totalOriginalPrice = totalOriginalPrice.add(originalPrice);
				totalPriceAfterPromocode = totalPriceAfterPromocode.add(priceAfterPromocode);

				sendMessage("Item: " + menuItem.getName() + ". Count: " + count + ". Original price: " + originalPrice
						+ ". Discount price: " + priceAfterPromocode);
			}
			sendMessage("Total price: " + totalPriceAfterPromocode + ". Total discount: "
					+ totalOriginalPrice.subtract(totalPriceAfterPromocode));
			sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID + ": continue order");
			sendMessage(CANCEL_ORDER_COMMAND_ID + ": cancel order");
			sendMessage("Selected promocode: " + enteredPromocode);
			sendMessage(ENTER_PROMOCODE_ID + ": enter promocode");
			sendMessage(PAY_COMMAND_ID + ": pay order");
		} else {
			// TODO использовать StringBuilder вместо +
			if (basketItems != null && !basketItems.isEmpty()) {
				BigDecimal totalOriginalPrice = new BigDecimal(0);
				BigDecimal totalDiscountPrice = new BigDecimal(0);

				for (Entry<IMenuItem, Integer> entry : basketItems.entrySet()) {
					IMenuItem menuItem = entry.getKey();
					Integer count = entry.getValue();

					BigDecimal originalPrice = customerSessionInterface.getOriginalPrice(menuItem, count);
					BigDecimal discountPrice = customerSessionInterface.getDiscountPrice(menuItem, count);

					totalOriginalPrice = totalOriginalPrice.add(originalPrice);
					totalDiscountPrice = totalDiscountPrice.add(discountPrice);

					sendMessage("Item:" + menuItem.getName() + ". Count:" + count + ". Original price: " + originalPrice
							+ ". Discount price: " + discountPrice);
				}

				sendMessage("Total price:" + totalDiscountPrice + ". Total discount: "
						+ totalOriginalPrice.subtract(totalDiscountPrice));
			} else {
				sendMessage("Basket is empty");
			}
			sendMessage(RETURN_TO_PREVIOUS_MENU_COMMAND_ID + ": continue order");
			sendMessage(CANCEL_ORDER_COMMAND_ID + ": cancel order");
			sendMessage(ENTER_PROMOCODE_ID + ": enter promocode");
			sendMessage(PAY_COMMAND_ID + ": pay order");
		}

	}

	private void removeOrder() {

		customerSessionInterface.clearBasket();
		enteredPromocode = null;
		sendMessage("order canceled");
	}

	private void enterPromoCode() {

		enteredPromocode = getEnteredPromocode();
		menuItemsAfterPromocode = customerSessionInterface.getDiscountPrice(customerSessionInterface.getBasket(),
				enteredPromocode);

		if (menuItemsAfterPromocode == null) {
			sendMessage("uncorrect promocode");
			enteredPromocode = null;
		} else {
			sendMessage("promocode applied");
		}
		showBasketMenuOptions();
	}

	private void payOrdered() {
		sendMessage("order paid");
		sendMessage("Your order number: " + customerSessionInterface.getCafe().createOrder());
		customerSessionInterface.getBasket().clear();
	}
}
