package program.database_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnect;

    public Connection getDbConnect()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnect = DriverManager.getConnection(connectionString, dbUser, dbPassword);

        return dbConnect;
    }

    public void register(String login, String password, String fullname, String phonenumber) {
        String insert = "INSERT INTO " + UsersTable.USERS_TABLE + "(" + UsersTable.LOGIN + ", " +
                UsersTable.PASSWORD + ", " + UsersTable.FULLNAME + ", " +
                UsersTable.PHONENUMBER + ")" + "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setString(3, fullname);
            prSt.setString(4, phonenumber);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addService(String name, String price) {
        String insert = "INSERT INTO " + ServicesTable.SERVICES_TABLE + "(" +
                ServicesTable.NAME + ", " + ServicesTable.PRICE + ") VALUES(?, ?)";

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, price);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(Users user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + UsersTable.USERS_TABLE + " WHERE " +
                UsersTable.LOGIN + "=? AND " + UsersTable.PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnect().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ObservableList<Services> get() {
        try {
            PreparedStatement prSt = getDbConnect().prepareStatement("SELECT * FROM " + ServicesTable.SERVICES_TABLE);

            ResultSet result = prSt.executeQuery();
            ObservableList<Services> list = FXCollections.observableArrayList();
            while (result.next()) { //Получение данных из столбцов базы данных
                Services services = new Services();

                services.setID(result.getString("id"));
                services.setName(result.getString("name"));
                services.setPrice(result.getString("price"));

                list.add(services);
            }
            return list;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}