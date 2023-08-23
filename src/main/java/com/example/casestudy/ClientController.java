package com.example.casestudy;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientController implements Initializable {
    private  Socket socket;
    @FXML
    private  TextArea textArea;
    @FXML
    private  TextField textField;
    JDBC jdbc = new JDBC();

    public void connect() throws IOException {
        int port_server = 8888;
        String ip_server = "localhost";
        socket = new Socket(ip_server,port_server);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    protected void send() throws IOException, SQLException {
        sendDataToServer(socket,textField);
        textField.clear();
        repDataFromServer(socket,textArea);
    }
    @FXML
    protected void delete() throws SQLException, IOException {
        jdbc.deleteData();
    }
    private void sendDataToServer(Socket socket , TextField textField) throws IOException, SQLException {
        OutputStream outputStream = socket.getOutputStream();
        String input = String.valueOf(textField.getText());
        String name = "Client";
        jdbc.save(name,input);
        byte[] sendData = input.getBytes();
        outputStream.write(sendData);
    }
    public void repDataFromServer(Socket socket, TextArea textArea) throws IOException, SQLException {
        InputStream inputStream = socket.getInputStream();
        textArea.setText(String.valueOf(jdbc.readMesage()));
        byte[] bytes = new byte[1024];
        int rep = inputStream.read(bytes);
        String repData = new String(bytes,0,rep);
        String name = "Server : ";
        textArea.appendText(name + repData + "\n");
    }
}