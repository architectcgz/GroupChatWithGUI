import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sat Apr 08 17:35:15 CST 2023
 */

/**
 * @author archi
 */
public class ServerFrame {
    private String server_Port;

    private Server server;
    private String msgToSend;
    public ServerFrame() {
        initComponents();

    }

//
    private void bt_startMouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {//点击左键
            try {
                if (server_Port == null) {
                    textArea.append("未输入端口值!" + "\n");
                } else {
                    ServerFrame.textArea.append("Server starting..." + "\n");
                    server = new Server(Integer.parseInt(server_Port));
                }
               server.startServer();
            } catch (IOException e1) {
                e1.printStackTrace();
                server.closeEverything();
            }


        }
    }

    private void portFieldKeyReleased(KeyEvent e) {
        if(e.getSource()==portField){
            this.server_Port=portField.getText();//端口值
        }

    }

    private void messageFieldKeyReleased(KeyEvent e) {
        if(e.getSource()==messageField){
            msgToSend=messageField.getText();
        }
    }

    //点击发送后将输入框中的内容置空
    private void bt_sayMouseClicked(MouseEvent e) {
        if(e.getButton()==1){//点击左键
            if(msgToSend!=null){
                server.sendMessage(msgToSend);
                textArea.append(msgToSend+"\n");
                msgToSend=null;
                messageField.setText(null);
            }
        }
    }

    private void bt_sayKeyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(msgToSend!=null){
                server.sendMessage(msgToSend);
                textArea.append(msgToSend+"\n");
                msgToSend=null;
                messageField.setText(null);
            }
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ServerUI = new JFrame();
        scrollPane1 = new JScrollPane();
        textArea = new JTextArea();
        panel1 = new JPanel();
        label1 = new JLabel();
        portField = new JTextField();
        bt_start = new JButton();
        messageField = new JTextField();
        bt_say = new JButton();

        //======== ServerUI ========
        {
            ServerUI.setTitle("\u670d\u52a1\u5668");
            Container ServerUIContentPane = ServerUI.getContentPane();
            ServerUIContentPane.setLayout(null);

            //======== scrollPane1 ========
            {

                //---- textArea ----
                textArea.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
                textArea.setEditable(false);
                scrollPane1.setViewportView(textArea);
            }
            ServerUIContentPane.add(scrollPane1);
            scrollPane1.setBounds(25, 95, 650, 270);

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder("\u670d\u52a1\u5668\u8bbe\u7f6e\uff1a"));
                panel1.setToolTipText("\u670d\u52a1\u5668\u8bbe\u7f6e\uff1a");
                panel1.setLayout(null);

                //---- label1 ----
                label1.setText("port:");
                label1.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
                panel1.add(label1);
                label1.setBounds(10, 30, 60, 30);

                //---- portField ----
                portField.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
                portField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        portFieldKeyReleased(e);
                    }
                });
                panel1.add(portField);
                portField.setBounds(60, 25, 465, 40);

                //---- bt_start ----
                bt_start.setText("Start");
                bt_start.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
                bt_start.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        bt_startMouseClicked(e);
                    }
                });
                panel1.add(bt_start);
                bt_start.setBounds(530, 25, 90, 35);
            }
            ServerUIContentPane.add(panel1);
            panel1.setBounds(30, 0, 640, 85);

            //---- messageField ----
            messageField.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
            messageField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    messageFieldKeyReleased(e);
                }
            });
            ServerUIContentPane.add(messageField);
            messageField.setBounds(25, 380, 535, 40);

            //---- bt_say ----
            bt_say.setText("Say");
            bt_say.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
            bt_say.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bt_sayMouseClicked(e);
                }
            });
            bt_say.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    bt_sayKeyPressed(e);
                }
            });
            ServerUIContentPane.add(bt_say);
            bt_say.setBounds(580, 380, 95, 40);

            ServerUIContentPane.setPreferredSize(new Dimension(700, 475));
            ServerUI.setSize(700, 475);
            ServerUI.setLocationRelativeTo(ServerUI.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
            ServerUI.setVisible(true);
            ServerUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame ServerUI;
    private JScrollPane scrollPane1;
    public static JTextArea textArea;
    private JPanel panel1;
    private JLabel label1;
    private JTextField portField;
    private JButton bt_start;
    private JTextField messageField;
    private JButton bt_say;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        new ServerFrame();
    }


}
