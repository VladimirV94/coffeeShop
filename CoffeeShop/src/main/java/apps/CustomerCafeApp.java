package apps;

import apps.interfaces.CustomerSessionInterface;
import apps.interfaces.ICommandsId;
import models.Cafe;

public class CustomerCafeApp implements ICafeApp, ICommandsId {
	private Cafe cafe;

	public CustomerCafeApp(Cafe cafe) {
		this.cafe = cafe;
	}

	public void start() {
		CustomerSessionInterface intarfaceManager = new CustomerSessionInterface(cafe);
		intarfaceManager.startSession();
	}
}
