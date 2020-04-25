package main;

import gui.controller.MainCONTROLLER;
import gui.model.MainMODEL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static final int AMOUNT_OF_FRAMES = 12;
    public static final int RADIOBUTTON_NUMBER = 3;
    public static final int PAGE_TABLE_COLUMN_WIDTH = 100;
    public static final int PAGE_TABLE_HEIGHT = 425;
    public static final int READ_WRITE_TABLE_COLUMN_WIDTH=100;

    public static final int PAGE_TABLE_LENGTH = 16;
    public static final String PAGE_TABLE_TRUE = "V";
    public static final String PAGE_TABLE_FALSE= "X";



    private static final String MAIN_TITLE = "PAGINATION EMULATOR";
    private static final String GUI_MAIN_VIEW_FXML = "/gui/view/MainView.fxml";
    private static final String GUI_VIEW_GENERAL_CSS = "/gui/view/general.css";
    private Stage primaryStage;
    private MainMODEL mainModel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainModel = new MainMODEL();
        this.primaryStage = primaryStage;
        showMain();
        mainModel.refresh();
    }

    private void showMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(GUI_MAIN_VIEW_FXML));
        Parent root = fxmlLoader.load();
        MainCONTROLLER mainController = fxmlLoader.getController();
        Scene scene = new Scene(root);



        scene.getStylesheets().add(GUI_VIEW_GENERAL_CSS);
        primaryStage.setScene(scene);
        primaryStage.setTitle(MAIN_TITLE);
//        primaryStage.resizableProperty().set(false);
        primaryStage.show();

        mainController.setModel(mainModel);
        mainModel.addObserver(mainController);

    }
}
