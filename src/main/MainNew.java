package main;

import gui.controller.MainController;
import gui.model.MainModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainNew extends Application {
    private static final String MAIN_TITLE = "PAGINATION EMULATOR";
    private static final String GUI_MAIN_VIEW_FXML = "/gui/view/MainView.fxml";
    private static final String GUI_VIEW_GENERAL_CSS = "/gui/view/general.css";
    public static int FRAME_NUMBER = 12;

    private Stage primaryStage;
    private MainModel mainModel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainModel = new MainModel();
        this.primaryStage = primaryStage;
        showMain();
        mainModel.refresh();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(GUI_MAIN_VIEW_FXML));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(GUI_VIEW_GENERAL_CSS);
        primaryStage.setScene(scene);
        primaryStage.setTitle(MAIN_TITLE);
        primaryStage.resizableProperty().set(false);
        primaryStage.show();

        mainController.setModel(mainModel);
        mainModel.addObserver(mainController);

    }
}
