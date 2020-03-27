package main;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public class Main_new extends Application {

    private Stage primaryStage;
    private Model model;
    private String title;

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new Model();
        this.primaryStage = primaryStage;
        showMain();
        model.refresh();
        this.title = "PAGINATION EMULATOR";
    }


    private void showMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/MainView.fxml"));
        Parent root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.resizableProperty().set(false);
        primaryStage.show();

        controller.setModel(model);
        model.addObserver(controller);

    }
}
