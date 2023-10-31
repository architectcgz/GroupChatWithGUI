import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sat Apr 08 19:21:43 CST 2023
 */



/**
 * @author archi
 */
public class ClientFrame {
    public ClientFrame() {
        initComponents();
    }
    private Client client;
    private String server_IP;
    private int server_Port=-1;

    private String msgToSend;
    private void bt_connectMouseClicked(MouseEvent e) {
        if(e.getButton()==1){
            if(server_IP==null) {
                textArea.append("请输入Server IP!"+"\n");
            } else if (server_Port==-1) {
                textArea.append("请输入Server Port!"+"\n");
            }else{
                client=new Client(server_IP,server_Port);
                client.listenForMessage();
            }
        }


    }

    private void ipFieldKeyReleased(KeyEvent e) {
        if(e.getSource()==ipField){
            server_IP= ipField.getText();
        }
    }

    private void portFieldKeyReleased(KeyEvent e) {
        if(e.getSource()==portField){
            server_Port= Integer.parseInt(portField.getText());
        }
    }

    private void wordsFieldKeyReleased(KeyEvent e) {
        if(e.getSource()==wordsField){
            msgToSend=wordsField.getText();
        }
    }

    private void bt_sayMouseClicked(MouseEvent e) {
        if(e.getButton()==1){
            if(msgToSend!=null){
                textArea.append(msgToSend+"\n");
                client.sendMessage(msgToSend);
                wordsField.setText(null);
                msgToSend=null;
            }

        }
    }

    //Enter也能发送消息
    private void bt_sayKeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(msgToSend!=null){
                textArea.append(msgToSend+"\n");
                client.sendMessage(msgToSend);
                wordsField.setText(null);
                msgToSend=null;
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ServerUI = new JFrame();
        panel1 = new JPanel();
        serverIP = new JLabel();
        ipField = new JTextField();
        serverPort = new JLabel();
        portField = new JTextField();
        bt_connect = new JButton();
        scrollPane1 = new JScrollPane();
        textArea = new JTextArea();
        wordsField = new JTextField();
        bt_say = new JButton();

        //======== ServerUI ========
        {
            ServerUI.setTitle("\u5ba2\u6237\u7aef");
            Container ServerUIContentPane = ServerUI.getContentPane();
            ServerUIContentPane.setLayout(null);

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder("\u5ba2\u6237\u7aef\u8bbe\u7f6e\uff1a"));
                panel1.setLayout(null);

                //---- serverIP ----
                serverIP.setText("Server IP:");
                serverIP.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
                panel1.add(serverIP);
                serverIP.setBounds(10, 30, 110, serverIP.getPreferredSize().height);

                //---- ipField ----
                ipField.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
                ipField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        ipFieldKeyReleased(e);
                    }
                });
                panel1.add(ipField);
                ipField.setBounds(115, 25, 145, 35);

                //---- serverPort ----
                serverPort.setText("Server Port:");
                serverPort.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
                panel1.add(serverPort);
                serverPort.setBounds(270, 30, 130, 27);

                //---- portField ----
                portField.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
                portField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        portFieldKeyReleased(e);
                    }
                });
                panel1.add(portField);
                portField.setBounds(390, 25, 145, 35);

                //---- bt_connect ----
                bt_connect.setText("Connect");
                bt_connect.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
                bt_connect.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        bt_connectMouseClicked(e);
                    }
                });
                panel1.add(bt_connect);
                bt_connect.setBounds(545, 25, bt_connect.getPreferredSize().width, 35);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            ServerUIContentPane.add(panel1);
            panel1.setBounds(15, 10, 655, 70);

            //======== scrollPane1 ========
            {

                //---- textArea ----
                textArea.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
                textArea.setEditable(false);
                scrollPane1.setViewportView(textArea);
            }
            ServerUIContentPane.add(scrollPane1);
            scrollPane1.setBounds(15, 90, 655, 280);

            //---- wordsField ----
            wordsField.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
            wordsField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    wordsFieldKeyReleased(e);
                }
            });
            ServerUIContentPane.add(wordsField);
            wordsField.setBounds(10, 390, 560, 45);

            //---- bt_say ----
            bt_say.setText("Say");
            bt_say.setFont(new Font("JetBrains Mono Medium", Font.BOLD, 16));
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
            bt_say.setBounds(585, 395, 100, 40);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < ServerUIContentPane.getComponentCount(); i++) {
                    Rectangle bounds = ServerUIContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = ServerUIContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                ServerUIContentPane.setMinimumSize(preferredSize);
                ServerUIContentPane.setPreferredSize(preferredSize);
            }
            ServerUI.setSize(705, 500);
            ServerUI.setLocationRelativeTo(ServerUI.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
        ServerUI.setVisible(true);
        ServerUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame ServerUI;
    private JPanel panel1;
    private JLabel serverIP;
    private JTextField ipField;
    private JLabel serverPort;
    private JTextField portField;
    private JButton bt_connect;
    private JScrollPane scrollPane1;
    public static JTextArea textArea;
    private JTextField wordsField;
    private JButton bt_say;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    public static void main(String[] args) {
        new ClientFrame();
    }
}
