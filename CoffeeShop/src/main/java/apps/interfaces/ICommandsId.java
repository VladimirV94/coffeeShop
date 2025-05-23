package apps.interfaces;

public interface ICommandsId {
	
	//XXX id не уникальны строго для удобства ввода в консоль
	// При реализации отправки id команды в CustomerSessionInterface через слушателей команд
	// это почти наверняка потребует уникальных id для всех возможных команд
	// Так же в идеале необходимо разграничить типизацию для id вводимых команд и 
	// ввода количества приобретаемых позиций меню
	
	static final int MENU_COMMAND_ID = 1;
	static final int ORDERMENU_COMMAND_ID = 2;
	static final int BASKET_COMMAND_ID = 5;
	static final int FIRST_CATEGORY_COMMAND_ID = 1;
	static final int FIRST_MENUITEM_COMMAND_ID = 1;
	
	static final int CANCEL_ORDER_COMMAND_ID = 1;
	static final int ENTER_PROMOCODE_ID = 2;
	static final int PAY_COMMAND_ID = 3;
	static final int RETURN_TO_MAINMENU_COMMAND_ID = 4;
	
	static final int RETURN_TO_PREVIOUS_MENU_COMMAND_ID = 0;
}
