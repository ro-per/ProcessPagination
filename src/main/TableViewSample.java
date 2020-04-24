package main;

import gui.model.PageTableENTRY;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TableViewSample extends Application {

    private final ObservableList<PageTableENTRY> data =
            FXCollections.observableArrayList();
    private TableView<PageTableENTRY> table = new TableView<PageTableENTRY>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn pageCOL = new TableColumn("Page");
        pageCOL.setMinWidth(100);
        pageCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("page"));
        pageCOL.setSortable(false);

        TableColumn presentBitCOL = new TableColumn("Present Bit");
        presentBitCOL.setMinWidth(100);
        presentBitCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("presentBit"));
        presentBitCOL.setSortable(false);

        TableColumn modifyBitCOL = new TableColumn("Modify Bit");
        modifyBitCOL.setMinWidth(200);
        modifyBitCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("modifyBit"));
        modifyBitCOL.setSortable(false);

        TableColumn lastTimeAccessedCOL = new TableColumn("Last Time Accessed");
        lastTimeAccessedCOL.setMinWidth(200);
        lastTimeAccessedCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("lastTimeAccessed"));
        lastTimeAccessedCOL.setSortable(false);

        TableColumn frameNumberCOL = new TableColumn("Frame Number");
        frameNumberCOL.setMinWidth(200);
        frameNumberCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("frameNumber"));
        frameNumberCOL.setSortable(false);

        data.add(new PageTableENTRY("A", "B", "C", "D", "E"));
        data.add(new PageTableENTRY("A", "B", "C", "D", "E"));
        table.setItems(data);
        table.getColumns().addAll(pageCOL, presentBitCOL, modifyBitCOL, lastTimeAccessedCOL, frameNumberCOL);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
/*
    public static class PageTableENTRY {

        private final SimpleStringProperty page;
        private final SimpleStringProperty presentBit;
        private final SimpleStringProperty modifyBit;


        private final SimpleStringProperty lastTimeAccessed;
        private final SimpleStringProperty frameNumber;

        public PageTableENTRY(String p, String pb, String mb, String lta, String fn) {
            this.page = new SimpleStringProperty(p);
            this.presentBit = new SimpleStringProperty(pb);
            this.modifyBit = new SimpleStringProperty(mb);
            this.lastTimeAccessed = new SimpleStringProperty(lta);
            this.frameNumber = new SimpleStringProperty(fn);
        }

        public String getPage() {
            return page.get();
        }

        public void setPage(String fName) {
            page.set(fName);
        }

        public String getPresentBit() {
            return presentBit.get();
        }

        public void setPresentBit(String fName) {
            presentBit.set(fName);
        }

        public String getModifyBit() {
            return modifyBit.get();
        }

        public void setModifyBit(String fName) {
            modifyBit.set(fName);
        }

        public String getLastTimeAccessed() {
            return lastTimeAccessed.get();
        }

        public void setLastTimeAccessed(String lastTimeAccessed) {
            this.lastTimeAccessed.set(lastTimeAccessed);
        }

        public SimpleStringProperty lastTimeAccessedProperty() {
            return lastTimeAccessed;
        }

        public String getFrameNumber() {
            return frameNumber.get();
        }

        public void setFrameNumber(String frameNumber) {
            this.frameNumber.set(frameNumber);
        }

        public SimpleStringProperty frameNumberProperty() {
            return frameNumber;
        }
    }*/
} 