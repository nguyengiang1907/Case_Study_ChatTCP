package com.example.casestudy;

import java.sql.*;

public class JDBC {
    String hostName = "localhost:3306";
    String dbName = "CASE_STUDY_JDBC";
    String userName = "root";
    String passwd = "giang";

    String connectionURL = "jdbc:mysql://" + hostName + "/" + dbName;

    public Connection connect() throws SQLException {
        Connection connection = null;

        connection = DriverManager.getConnection(connectionURL, userName, passwd);
        return connection;
    }

    public void save(String name, String data) throws SQLException {
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.connect();

        String query = "INSERT INTO SAVE(name,mesage) VALUES(?,?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, data);
        preparedStatement.executeUpdate();
        conn.close();
    }

    public StringBuilder readMesage() throws SQLException {
        JDBC jdbc = new JDBC();
        Connection coon = jdbc.connect();

        String query = "SELECT * FROM SAVE";
        Statement statement = null;

        statement = coon.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        StringBuilder stringBuilder = new StringBuilder();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String message = resultSet.getString("mesage");
            stringBuilder.append(name + " : " + message + "\n");
        }
        return stringBuilder;
    }

    public void deleteData() throws SQLException {
        JDBC jdbc = new JDBC();
        Connection coon = jdbc.connect();

        String query = "DELETE FROM SAVE";
        PreparedStatement sttm = coon.prepareStatement(query);
        sttm.executeUpdate();
        coon.close();
    }
}
