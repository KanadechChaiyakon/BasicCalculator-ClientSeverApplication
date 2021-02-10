package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int SERVER_PORT = 5060;
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String input, output;

    public ServerSocket start()throws IOException{

        serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Server is online");

//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run(){
//                try {
//                    System.out.println("Server is listening in port: " + SERVER_PORT);
//                    socket = serverSocket.accept();
//                    System.out.println( socket + "Connected");
//                    printWriter = new PrintWriter(socket.getOutputStream(),true);
//                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                }catch (Exception e){
//                    System.err.println(e.getMessage());
//                }
//            }
//        });
//
//        thread.start();

        return  serverSocket;
    }

    public void stop() throws IOException{
        printWriter.close();
        bufferedReader.close();
        serverSocket.close();
        socket.close();
    }

    public void handleRequest(ServerSocket serverSocket){
        System.out.println("Server is listening in port: " + SERVER_PORT);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        System.out.println(socket + "Connected");
//                        printWriter = new PrintWriter(socket.getOutputStream());
//                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                    try {
//                        String request = bufferedReader.readLine();
                        String request = dataInputStream.readUTF();
                        System.out.println("Client input Received: " + request);
//                        printWriter.println("It is :" + request);
//                        printWriter.flush();
                        dataOutputStream.writeUTF("This is a "+ request);
                        dataOutputStream.flush();
                        socket.close();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                }
            }
        });

        thread.start();
    }

    public static void main(String[] args)throws IOException {
        Server server = new Server();
        ServerSocket serverSocket = server.start();
        if(serverSocket != null){
            server.handleRequest(serverSocket);
        }
    }

}
