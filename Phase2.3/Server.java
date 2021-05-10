import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private final int serverport;
    private ArrayList<ServerWorker> workerlist = new ArrayList<>();

    public Server(int serverport) {
        this.serverport = serverport;
    }

    //phase 2.3 way for serverworker to access other server workers
    public List<ServerWorker> getworkerList(){
        return workerlist;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverport);
            while (true){
                System.out.println("Listening on port " + serverport +"...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection on " + clientSocket);
                // phase 2.3to make severworkers to access the server instance need to pass the server as part of the parameter
                // i.e. this.sever
                ServerWorker worker = new ServerWorker(this, clientSocket);
                workerlist.add(worker);
                worker.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
