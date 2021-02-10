package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String output;


    public void StartConnect() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), Server.SERVER_PORT);
            System.out.println("Connecting to port: "+ Server.SERVER_PORT);
            printWriter = new PrintWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public String sendInput(String input) throws IOException {
//        printWriter.println(input);
//        printWriter.flush();
        dataOutputStream.writeUTF(input);
        dataOutputStream.flush();
        try {
//            output = bufferedReader.readLine();
            output = dataInputStream.readUTF();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return output;
    }

    public void StopConnect() throws IOException{
        printWriter.close();
        bufferedReader.close();
        socket.close();
    }

}
