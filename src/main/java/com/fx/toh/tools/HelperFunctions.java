package com.fx.toh.tools;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelperFunctions {

    private static final Logger _log = LogManager.getLogger(HelperFunctions.class);

    public static void centerWindow(Window window) {
        window.addEventHandler(WindowEvent.WINDOW_SHOWN, (WindowEvent event) -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((screenBounds.getHeight() - window.getHeight()) / 2);
        });
    }

    public Node loadFxml(ResourceBundle bundle, String path, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path), bundle);
            loader.setController(controller);
            Node node = loader.load();
            return node;
        } catch (IOException ex) {
            _log.error(ex.getMessage());
        }
        return null;
    }

    public static Tab addTab(ResourceBundle bundle, TabPane tabPane, String path, Object controller, String tabName) {
        long start = System.currentTimeMillis();
        Tab tab = new Tab(tabName);
        tabPane.getTabs().add(tab);
        HelperFunctions helperFunctions = new HelperFunctions();
        Node node = helperFunctions.loadFxml(bundle, path, controller);
        node.setUserData(controller);
        tab.setContent(node);
        long end = System.currentTimeMillis();
        System.out.println("Loadtime (" + controller.toString() + ") in ms: " + (end - start));
        return tab;
    }

    public static BorderPane createTab(ResourceBundle bundle, String path, Object controller) {
        long start = System.currentTimeMillis();
        BorderPane borderPane = new BorderPane();
        HelperFunctions helperFunctions = new HelperFunctions();
        Node node = helperFunctions.loadFxml(bundle, path, controller);
        node.setUserData(controller);
        borderPane.setCenter(node);
        long end = System.currentTimeMillis();
        System.out.println("Loadtime (" + controller.toString() + ") in ms: " + (end - start));
        return borderPane;
    }

    public static Node addPlugin(ResourceBundle bundle, String path, Object controller) {
        long start = System.currentTimeMillis();
        HelperFunctions helperFunctions = new HelperFunctions();
        Node node = helperFunctions.loadFxml(bundle, path, controller);
        node.setUserData(controller);
        long end = System.currentTimeMillis();
        System.out.println("Loadtime (" + controller.toString() + ") in ms: " + (end - start));
        return node;
    }
}
