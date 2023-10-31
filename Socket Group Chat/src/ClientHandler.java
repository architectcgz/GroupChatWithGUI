import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//实现多线程，使每个ClientHandler处理的对象都执行一个独立的线程
//不必等待某个人发送信息后自己才能发送信息
public class ClientHandler implements Runnable {

    // 记录每一个client发送的信息，一个client发送的信息所有人都能看见
    // 可以遍历ArrayList将信息发送给所有 client
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    // 由server类传递来的socket,用于server与client通信
    private Socket socket;

    // 用于读取client发送的特定信息
    private BufferedReader bufferedReader;
    // 用于client发送信息
    private BufferedWriter bufferedWriter;
    // 所有的client发送的信息将会通过 ArrayList广播到世界

    private String clientUsername;

    // socket代表了server和clientHandler的联系
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            // 分别是用于发送和接受信息的
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 接收用户姓名，第一行输入的是姓名
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);// 将自己加入到clientHandler

            broadcastMessage("SERVER:  " + clientUsername + " has entered the chat!");

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);//出错关掉所有东西
        }

    }

    @Override
    public void run() {
        String messageFromClient;
        //使用循环保证能够一直接收和发送信息
        while (socket.isConnected()) {
            try {
                //使用多线程保证每个client发送信息时，其余的client部分应用不会停止
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    //通过遍历将消息发送给所有人
    public void broadcastMessage(String messageToSend) {

        for (ClientHandler clientHandler : clientHandlers) {
            try { //不发送给自己
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    //将当前客户端的输出流的缓冲区中的数据写入一行，并且移动到下一行，以便下一个消息可以开始在新行上写入。
                    clientHandler.bufferedWriter.newLine();
                    // 是将输出流的缓冲区立即刷新，确保所有待发送的数据被及时写入到网络中。
                    //如果没有调用该方法，输出流缓冲区的数据不一定会立即写入到网络中，
                    //而是可能会一直积累到一定的大小再一次性写入，这可能会导致消息传输的延迟。
                    //因此，在发送完一条消息后，最好立即调用 flush() 方法以确保数据能够及时传输到客户端。
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }

        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat");
    }

    //将client handler从client handlers 中移除，关掉输入，输出缓冲区和客户端
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                //关闭socket的输入输出流
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}