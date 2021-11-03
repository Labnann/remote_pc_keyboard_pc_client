package AndroidKeyboardReceiver;

import org.junit.Assert;
import org.junit.Test;

public class TestMessage{
     @Test
    public void testMessage(){
         Message message = new Message("100@habi");
         Assert.assertEquals(100,message.getKeyCode());
         Assert.assertEquals("habi",message.getText());
     }
}
