import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 陈志峰
 */
public class Server {
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    Socket clientSocket;
    ServerSocket serverSocket;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    //
    public void startServer() {
        new Thread(() -> {
            try {
                clientSocket = serverSocket.accept();
                ServerFrame.textArea.append("Client connected…" + "\n");
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (clientSocket.isConnected()) {
                    String line = bufferedReader.readLine();
                    ServerFrame.textArea.append(line + "\n");//将读到的信息添加到textArea中
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything();
            }

        }
        ).start();

    }


    public void sendMessage(String msgToSend) {
        try {
            bufferedWriter.write("Server: " + msgToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    public void closeEverything() {

        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

