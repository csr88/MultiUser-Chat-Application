import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Phase1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the listening port: ");
        int port = sc.nextInt();

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                System.out.println("Listening on port " + port);
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(("Hello World\n").getBytes(StandardCharsets.UTF_8));
                serverSocket.close();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
