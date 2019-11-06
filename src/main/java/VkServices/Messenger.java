package VkServices;

import com.vk.api.sdk.objects.messages.Message;

public class Messenger implements Runnable{
    private Message message;
    public Messenger(Message msg){
        this.message = msg;
    }

    @Override
    public void run() {
        Commander.execute(message);
    }
}
