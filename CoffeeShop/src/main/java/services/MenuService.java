package services;

import models.Menu;

public class MenuService {

	private Menu menu;

	public Menu getСurrentMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	// TODO реализация работы с несколькими меню. Возможность их редактировать,
	// добавлять и удалять
}
