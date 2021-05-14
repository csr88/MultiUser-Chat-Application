import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ServerWorker extends  Thread {


    private final Socket clientSocket;
    private String login = null;

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
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine())!=null){
            //splits words using spaces
            String[] tokens = StringUtils.split(line);
            if (tokens!=null && tokens.length>0) {
                //the first word is our command
                String cmd = tokens[0];

                if ("exit".equalsIgnoreCase(cmd)) {
                    break;
                } else if("login".equalsIgnoreCase(cmd)){
                    handleLogin(outputStream,tokens);
                }

                else {
                    String msg = ("Unknown " + cmd + "\n");
                    outputStream.write(msg.getBytes());
                }

            }
        }
        clientSocket.close();
    }

   public void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        //input has 3 words
        if (tokens.length==3){
            //second word is the username
            String login = tokens[1];
            //third word is the password
            String password = tokens[2];

            if ((login.equalsIgnoreCase("guest")&&password.equals("guest123"))
                    || (login.equalsIgnoreCase("anon") && password.equals("anonymous"))  ||
                    login.equalsIgnoreCase("shishir") && password.equals("shishir123")) {

                String msg = "Login Successfull!";
                outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
                this.login = login;
                System.out.println("\nUser " + this.login + " has logged in.");
            }else{
                    String msg = "Login Failed!";
                    outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
                }

        }
   }


}
