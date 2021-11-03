package AndroidKeyboardReceiver;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.*;


public class  AndroidKeyboardReceiver extends Application {

    Button button = new Button();
    TextField portInput = new TextField();
    TextArea clientOutputArea = new TextArea();
    Text text = new Text();
    Server server = new Server();
    public static void main(String[] args) {
        launch(args);
    }

        @Override
        public void start(Stage primaryStage) throws Exception {
            System.out.println("STARTT");
            Group root = new Group();

            Scene scene;

            configureButton();

            configPortInputField();

            configAddressInputField();

            configClientOutputArea();


            root.getChildren().addAll(button,portInput,text, clientOutputArea);
            scene = new Scene(root,300,300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Android Keyboard Listener");
            primaryStage.show();
            System.out.println("aaa");
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        server.close();

                    } catch (IOException e) {
                        System.out.println("Error closing port");
                    }
                    finally {
                        System.out.println("Ports closed");
                    }


                }
            });



        }

    private void configPortInputField() {
        portInput.setLayoutX(10);
        portInput.setLayoutY(50);
        portInput.setText("6777");
    }

    private void configClientOutputArea(){
        clientOutputArea.setLayoutX(10);
        clientOutputArea.setLayoutY(140);
        clientOutputArea.setMaxSize(250,150);
        clientOutputArea.setWrapText(true);
        clientOutputArea.setText("এখানে পাঠানো লেখা প্রদর্শিত হবে।");
        server.setClientOutputArea(clientOutputArea);


    }

    private void configAddressInputField() {


        text.setLayoutX(10);
        text.setLayoutY(120);
        text.setText("AAAAAAAAAAAAAAa?");
        try {
            text.setText(InetAddress.getLocalHost().toString());
            NetworkInterface.getByIndex(1);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    private void configureButton() {
        button.setLayoutX(200);
        button.setLayoutY(50);
        button.setText("Start!");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("CLICK!");
                server.setPort(Integer.valueOf(portInput.getText()));
                server.start();
            }
        });

    }




}
