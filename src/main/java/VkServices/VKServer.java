package VkServices;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VKServer {
    public static bot bot;
    static {
        try {
            bot = new bot();
        }catch (ApiException|ClientException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NullPointerException,ApiException,InterruptedException {
        System.out.println("Running serv...");
        while (true){
            Thread.sleep(300);
            try {
                Message message = bot.getMessage();
                if (message != null){
                    ExecutorService exec = Executors.newCachedThreadPool();
                    exec.execute(new Messenger(message));
                }
            } catch(ClientException e) {
                System.out.println("Troubles...");
                final int RECONNECT_TIME = 10000;
                System.out.println("I'll try reconnect in " + RECONNECT_TIME/1000+" seconds");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
}
