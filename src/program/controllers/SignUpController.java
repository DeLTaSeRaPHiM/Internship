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

public class SignUpController {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private TextField phonenumberField;

    @FXML
    private TextField fullNameField;

    private static Logger logger;

    static {
        try (FileInputStream configFile = new FileInputStream("src\\program\\logs.config")) {
            LogManager.getLogManager().readConfiguration(configFile);
            logger = Logger.getLogger(LoggerMain.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEnterSignUpBtn(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        String comfPass = confirmPasswordField.getText();
        String phonenumber = phonenumberField.getText();
        String name = fullNameField.getText();

        DatabaseHandler dbHandler = new DatabaseHandler();

        if (password.equals(comfPass)) {
            dbHandler.register(login, password, name, phonenumber);
            logger.log(Level.INFO, "Зарегистрирован новый аккаунт");
            openNewWindow("/program/screens/MainScreen.fxml");
        }
    }

    public void openNewWindow(String window) {
        SignUpButton.getScene().getWindow().hide();

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
