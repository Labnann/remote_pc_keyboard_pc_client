package AndroidKeyboardReceiver;


import javafx.scene.text.Text;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

import javafx.scene.control.TextArea;
import org.junit.Test;

public class Server implements Runnable{
    private  ServerSocket serverSocket;
    private  Socket clientSocket;
    private  InputStreamReader inputStreamReader;
    private  BufferedReader bufferedReader;



    private  Robot robot;
    private TextArea clientOutputArea;
    private String message;

    private Thread thread;
    private int port;

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void setClientOutputArea(TextArea clientOutputArea) {
        this.clientOutputArea = clientOutputArea;
    }

    public void setPort(int port) {
        this.port = port;
    }


    void runServer(int port) {




        try {
            serverSocket = new ServerSocket(port);
            robot = new Robot();
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Couln't listen to Port");
        }
        catch (AWTException e){
            e.printStackTrace();
            System.out.println("Robot Error!!");
        }

        System.out.println("Started. Listening...");

        while (true) {
            try {

                clientSocket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(clientSocket.getInputStream(),"UTF-8");
                Scanner clientScanner = new Scanner(clientSocket.getInputStream());
                Message messageFromClient = new Message(clientScanner.nextLine());

                 message = messageFromClient.getText();



                robotPressesKey();
                handleKeyCode(messageFromClient.getKeyCode());

                System.out.println(message);

                inputStreamReader.close();
                clientSocket.close();
            } catch (IOException | InterruptedException ex) {
                System.out.println("Unable to read message");
                break;
            }

        }
    }

    private void handleKeyCode(int lastKey) {
        System.out.println(lastKey);
        switch (lastKey){

            case 10:{
                System.out.println("Enter");
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                break;
            }
            case 33:{
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                break;
            }
            case 63:{
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SLASH);
                robot.keyRelease(KeyEvent.VK_SLASH);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                break;
            }
            case 46:{
           //     robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_PERIOD);
                robot.keyRelease(KeyEvent.VK_PERIOD);
                //  robot.keyRelease(KeyEvent.VK_SHIFT);

            }

        }
    }

    void robotPressesKey() throws InterruptedException {

        clientOutputArea.setText(message);
        if(!(message.equals("")||message==null))
        {StringSelection stringSelection = new StringSelection(message);
        clipboard.setContents(stringSelection,stringSelection);
//        robot.wait(5000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);}

    }

    @Override
    public void run() {
        runServer(port);
    }
    public void start(){
        thread = new Thread(this);
        thread.start();

    }
    public void close() throws IOException {
        if(inputStreamReader!=null)
        inputStreamReader.close();
        if(clientSocket!=null)
        clientSocket.close();
        if(serverSocket!=null)
        serverSocket.close();

    }
}

