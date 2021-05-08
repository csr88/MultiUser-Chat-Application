import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ServerWorker extends  Thread {

    private final Socket clientSocket;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket(clientSocket);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void handleClientSocket(Socket clientSocket) throws IOException, InterruptedException {
        OutputStream outputStream = clientSocket.getOutputStream();
        for(int i=0;i<10;i++){
            outputStream.write(("Datetime is " + new Date() + "\n").getBytes(StandardCharsets.UTF_8));
            Thread.sleep(1000);
        }
        clientSocket.close();

    }
}
