package VkServices;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;

public class VkManager {
    public static bot bot;
    static {
        try{
            bot = new bot();
        }catch (ApiException|ClientException e){
            e.printStackTrace();
        }

    }
    public void sendMessage(String msg, int chatId){
        if (msg == null){
            System.out.println("Null message");
            return;
        }
        try {
            bot.getVk().messages().send(bot.getActor()).peerId(chatId).message(msg).execute();
        }catch (ApiException|ClientException e){
            System.out.println("ERROR while trying send message");
            e.printStackTrace();
        }
    }

    public MessagesSendQuery getSendQuery(){
        return  bot.getVk().messages().send(bot.getActor());
    }
    public static UserXtrCounters getUserInfo(int id){
        try {
            return bot.getVk().users().get(bot.getActor()).userIds(String.valueOf(id))
                    .execute().get(0);
        }catch (ApiException|ClientException e){
            System.out.println("ERROR Troubles with getting user info");
            e.printStackTrace();
        }
        return null;
    }
}
