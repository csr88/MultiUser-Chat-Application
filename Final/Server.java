import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private final int serverPort;

    private ArrayList<ServerWorker> workerList = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while(true) {
                System.out.println("\nListening on port " + serverPort +"...");
                Thread.sleep(1000);
                System.out.println("Accepting client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                ServerWorker worker = new ServerWorker(this, clientSocket);
                workerList.add(worker);
                worker.start();





            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        workerList.remove(serverWorker);
    }

    public static void fileDownload(Socket clientSocket) throws IOException {
        //file sharing
        //reading the file from the folder
        FileInputStream fr = new FileInputStream("/home/shishir/Documents/file.txt");
        byte b[] = new byte[2002]; //creating a byte array to input the size of the file here taking random size
        fr.read(b,0,b.length); //read method to read the file from 0 to end and store it into a new variable 'b'
        //now send the file usign stream
        OutputStream os = clientSocket.getOutputStream();
        //read the b variable and send the file from 0 to end(ie. b.length)
        os.write(b,0,b.length);
    }

}
