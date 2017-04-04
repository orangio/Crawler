package crawler.GUI_fx;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCharacterCombination;

/**
 * Created by Filip on 03.04.2017.
 */
public class CustomMenuBar extends MenuBar {


public CustomMenuBar() {
    Menu programMenu = new Menu("Program");
    MenuItem exitItem = new MenuItem("Close");
    Menu aboutMenu = new Menu("About");
    MenuItem aboutItem = new MenuItem("About");

    exitItem.setAccelerator(KeyCharacterCombination.keyCombination("Ctrl+C"));
    exitItem.setOnAction(event -> System.exit(0));

    aboutItem.setOnAction(event -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Program Crawler, for Java classes");
        alert.setContentText("Made by Filip Bartman, AGH UST 2017");
        alert.showAndWait();
    });
        aboutMenu.getItems().addAll(aboutItem);
        programMenu.getItems().addAll(exitItem);

        this.getMenus().addAll(programMenu, aboutMenu);
}
}
