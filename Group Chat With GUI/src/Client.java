import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * @author 陈志峰
 */
public class Client {

    Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final String  username="LocalClient";

    public Client(String serverIP,int port){
        try{
            socket=new Socket(serverIP,port);
            //初始化输入流缓冲区和输出流缓冲区
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ClientFrame.textArea.append("Connect to server…\n");
        }catch (ConnectException e){
            ClientFrame.textArea.append("服务器端口"+port+"未开启\n");
            closeEverything();
        }catch (IOException e1){
            e1.printStackTrace();
            closeEverything();
        }

    }
    //发送信息时将信息写入到输出流即可
    public void sendMessage(String msgToSend) {
        try {
                bufferedWriter.write(username+": "+msgToSend);
                bufferedWriter.newLine();//每次写完一行后换行
                bufferedWriter.flush();//将缓冲区中的内容刷新到道输出流中
        } catch (IOException e) {
            closeEverything();
        }
    }

    //为每个client创建 收信息的新线程
    public void listenForMessage() {
        new Thread(() -> {
            try {
                while(socket.isConnected()) {
                    ClientFrame.textArea.append(bufferedReader.readLine()+"\n");
                }
            } catch (IOException e) {
                closeEverything();
            }

        }
        ).start();
    }
    public void closeEverything() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
