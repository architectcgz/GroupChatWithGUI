
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            //当ServerSocket没关闭时执行
            while (!serverSocket.isClosed()) {
                //该方法会阻塞程序，直到有客户端连接到服务器，
                //然后返回一个新的Socket对象，
                //该Socket对象可以用于与客户端通信
                //每次创建新的socket端，都要创建一个新的线程，以此保证多人能够同时对话
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);//加入线程
                thread.start();//运行这个线程

            }
        } catch (IOException e) {
            closeServerSocket();
        }


    }

    //如果出错或者正常退出调用这个函数
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();//关掉这个server-socket
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);//在端口1234上运行
        Server server = new Server(serverSocket);
        server.startServer();
    }

}



