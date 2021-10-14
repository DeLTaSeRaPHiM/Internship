package program.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import program.LoggerMain;
import program.database_controller.DatabaseHandler;
import program.database_controller.Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorkScreenController {
    private ObservableList<Services> DB = FXCollections.observableArrayList();

    @FXML
    private TableView<Services> Table;

    @FXML
    private TableColumn<Services, String> idColumn;

    @FXML
    private TableColumn<Services, String> serviceNameColumn;

    @FXML
    private TableColumn<Services, String> priceColumn;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateBtn;

    private static Logger logger;

    static {
        try (FileInputStream configFile = new FileInputStream("src\\program\\logs.config")) {
            LogManager.getLogManager().readConfiguration(configFile);
            logger = Logger.getLogger( LoggerMain.class.getName() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onClickAdd(ActionEvent event) {
        logger.log(Level.INFO, "Добавление нового значения...");
        openNewWindow("/program/screens/AddScreen.fxml");
    }

    @FXML
    void onClickDelete(ActionEvent event) {

    }

    @FXML
    void onClickUpdate(ActionEvent event) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        DatabaseHandler dbHandler = new DatabaseHandler();

        DB = dbHandler.get();
        Table.setItems(DB);
        logger.log(Level.INFO, "Обновление таблицы...");
    }

    public void openNewWindow(String window) {
        addBtn.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

}
