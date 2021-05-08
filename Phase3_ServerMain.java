import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the listening port: ");
        int port = sc.nextInt();
try {
    ServerSocket serverSocket = new ServerSocket(port);
    while (true){
        System.out.println("Listening on port " + port +"...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connection on " + clientSocket);
        ServerWorker worker = new ServerWorker(clientSocket);
        worker.start();
    }

} catch (IOException e) {
    e.printStackTrace();
}

    }
}
