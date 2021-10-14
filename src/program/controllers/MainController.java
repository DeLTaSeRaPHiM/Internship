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
import program.database_controller.Users;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainController {

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button enterButton;

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
    void onEnterBtnLogin(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        loginUser(login, password);
    }

    @FXML
    void onEnterBtnSignUp(ActionEvent event) {
        logger.log(Level.INFO, "Регистрация в приложении...");
        openNewWindow("/program/screens/SignUpScreen.fxml", signUpButton);
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

    private void loginUser(String loginText, String passwordText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Users user = new Users();
        user.setLogin(loginText);
        user.setPassword(passwordText);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter >= 1) {
            logger.log(Level.INFO, "Авторизация в приложении...");
            openNewWindow("/program/screens/WorkScreen.fxml", enterButton);
        }
    }

}
