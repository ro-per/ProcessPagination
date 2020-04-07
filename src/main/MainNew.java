package main;

import gui.controller.FileChooserController;
import gui.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.model.MainModel;

import java.io.IOException;

public class MainNew extends Application {

    private Stage primaryStage;
    private MainModel mainModel;
    private static final String mainTitle = "PAGINATION EMULATOR";
    private static final String fileChooserTitle = "FILE CHOOSER";
    private static final String mainViewLocation = "/gui/view/MainView.fxml";
    private static final String fileChooserLocation = "/gui/view/FileChooser.fxml";
    private static final String cssLocation = "/gui/view/general.css";
    public static int FRAMENUMBER=6;



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainModel = new MainModel();
        this.primaryStage = primaryStage;
        showMain();
        //showFileChooser();
        mainModel.refresh();
    }


    public static void main(String args[]) {
        launch(args);
    }


    private void showMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(mainViewLocation));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssLocation);
    primaryStage.setScene(scene);
        primaryStage.setTitle(mainTitle);
        //primaryStage.resizableProperty().set(false);
        primaryStage.show();

        mainController.setModel(mainModel);
        mainModel.addObserver(mainController);

    }

    private void showFileChooser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fileChooserLocation));
        Parent root = fxmlLoader.load();
        FileChooserController fileChooserController = fxmlLoader.getController();

        Stage stage = new Stage();

        stage.setTitle(fileChooserTitle);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(800);
        stage.setY(300);
        stage.show();

        fileChooserController.setModel(mainModel);
        mainModel.addObserver(fileChooserController);
    }
}
