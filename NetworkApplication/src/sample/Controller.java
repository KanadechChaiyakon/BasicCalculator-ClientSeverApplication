package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;

public class Controller {
    private Client client;
    private Server server;

    @FXML
    private Button connect, disconnect;

    @FXML
    private TextField input;

    @FXML
    private Label status, output;

    public void initialize() throws IOException {
        client = new Client();
        server = new Server();
        ServerSocket serverSocket = server.start();
        if(serverSocket != null){
            server.handleRequest(serverSocket);
        }
        client.StartConnect();
    }

    @FXML
    private void submit(Event event)throws IOException{
        String out = client.sendInput(input.getText());
        output.setText(out);
    }

    @FXML
    public void connect(Event event)throws IOException{
        client.StartConnect();
    }

    @FXML
    public void disconnect(Event event)throws IOException{
        client.StopConnect();
        server.stop();
        status.setText("Status: Disconnected");
    }


}
