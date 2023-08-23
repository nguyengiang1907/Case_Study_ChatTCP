package com.example.casestudy;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TableHeaderRow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.Collection;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    private Socket socket;
    private ServerSocket serverSocket;
    JDBC jdbc = new JDBC();


    private void connect() throws IOException {
        int port_server = 8888;
        serverSocket = new ServerSocket(port_server);
        socket = serverSocket.accept();
        System.out.println("Server đang đươc kết nối .....");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connect();
            repDataFromClient(socket, textArea);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void send() throws IOException, SQLException {
        sendDataToClient(socket, textField);
        textField.clear();
        repDataFromClient(socket, textArea);
    }

    @FXML
    protected void delete() throws SQLException, IOException {
        jdbc.deleteData();
    }

    public void repDataFromClient(Socket socket, TextArea textArea) throws IOException, SQLException {
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int rep = inputStream.read(bytes);
        textArea.setText(String.valueOf(jdbc.readMesage()));
        String repData = new String(bytes, 0, rep);
//        textArea.appendText(repData + "\n");
    }

    private void sendDataToClient(Socket socket, TextField textField) throws IOException, SQLException {
        OutputStream outputStream = socket.getOutputStream();
        String name = "Server";
        String input = String.valueOf(textField.getText());
        jdbc.save(name, input);
        byte[] bytes = input.getBytes();
        outputStream.write(bytes);
    }
}