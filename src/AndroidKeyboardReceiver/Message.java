package AndroidKeyboardReceiver;


import org.junit.Assert;
import org.junit.Test;

class Message {

    private int keyCode;
    private String text;
    public int getKeyCode(){
        return keyCode;
    }
    public String getText(){
        return text;
    }
    Message(String received){
        System.out.println(received);
        String[] splitted = received.split("@",2);
        keyCode = Integer.parseInt(splitted[0]);
        text = splitted[1];
    }


}
