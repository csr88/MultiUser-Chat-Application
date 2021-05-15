import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

    public static void sendFile(Socket clientSocket) throws IOException {
        OutputStream os = clientSocket.getOutputStream();

        //file sharing
        
        //getting file to download and file destination

        os.write(("Enter the file to download: ").getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String filetodownload;
        filetodownload = "/home/shishir";
        filetodownload += reader.readLine();

        os.write(("Enter the file destination: ").getBytes(StandardCharsets.UTF_8));
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream));
        String filedestination;
        filedestination = "/home/shishir";
        filedestination += reader.readLine();



        //Specify the file
        File file = new File(filetodownload);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        //Get socket's output stream
        //Read File Contents into contents array
        byte[] contents;
        long fileLength = file.length();
        long current = 0;
        long start = System.nanoTime();
        while(current!=fileLength){
            int size = 10000;
            if(fileLength - current >= size)
                current += size;
            else{
                size = (int)(fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
//            os.write(contents);
            FileOutputStream fos = new FileOutputStream(filedestination);
            byte[] s = contents;
            fos.write(s);
            System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
        }

        System.out.println("\nFile sent succesfully!");

    }

}
