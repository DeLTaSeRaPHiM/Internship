package program.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.LoggerMain;
import program.database_controller.DatabaseHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AddScreenController {

    @FXML
    private Button backBtn;

    @FXML
    private Button addBtn;

    @FXML
    private TextField priceField;

    @FXML
    private TextField nameField;

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
    void onClickAddBtn(ActionEvent event) {
        String name = nameField.getText();
        String price = priceField.getText();

        DatabaseHandler dbHandler = new DatabaseHandler();

        dbHandler.addService(name, price);

        logger.log(Level.INFO, "Добавлено новое значение...");
        openNewWindow("/program/screens/WorkScreen.fxml", addBtn);
    }

    @FXML
    void onClickBackBtn(ActionEvent event) {
        logger.log(Level.INFO, "Отмена добавления нового значения и возврат к таблице...");
        openNewWindow("/program/screens/WorkScreen", backBtn);
    }

    public void openNewWindow(String window, Button btn) {
        btn.getScene().getWindow().hide();

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
        stage.showAndWait();
    }
}
