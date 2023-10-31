
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket,String username) {
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMessage() {
        try {
            bufferedWriter.write(username);//加入会话首先要输入姓名
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner=new Scanner(System.in);
            while(socket.isConnected()) {
                String messageToSend=scanner.nextLine();
                bufferedWriter.write(username+": "+messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
           closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    //为每个client创建 收信息的新线程
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                try {
                    while(socket.isConnected()) {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);

                    }
                } catch (IOException e) {
                    closeEverything(socket,bufferedReader,bufferedWriter);
                }

            }
        }
        ).start();

    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader!=null)
                bufferedReader.close();
            if(bufferedWriter!=null)
                bufferedWriter.close();
            if(socket!=null){
                //关闭socket的输入输出流
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) throws IOException {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username=scanner.nextLine();
        Socket socket=new Socket("localhost",1234);
        Client client=new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }


}
