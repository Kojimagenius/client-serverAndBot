package VkServices;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class bot {
    private VkApiClient vk;
    private static int ts;
    private GroupActor actor;
    private static int maxMessageId = -1;



   public bot()throws ClientException, ApiException{
       TransportClient transportClient = HttpTransportClient.getInstance();
       vk = new VkApiClient(transportClient);

       Properties properties = new Properties();
       int groupId;
       String token;
       try{
           properties.load(new FileInputStream("src/main/resources/vk.properties"));
           groupId = Integer.valueOf(properties.getProperty("groupId"));
           token = properties.getProperty("token");
           actor = new GroupActor(groupId,token);
           ts = vk.messages().getLongPollServer(actor).execute().getTs();
       }catch (IOException e){
           e.printStackTrace();
           System.out.println("error occured while trying to access VkServices.bot's properties");
       }
   }
   public Message getMessage() throws ClientException, ApiException{
       MessagesGetLongPollHistoryQuery eventsQuery = vk.messages().getLongPollHistory(actor).ts(ts);
       if (maxMessageId>0){
           eventsQuery.maxMsgId(maxMessageId);
       }
       List<Message> messages = eventsQuery.execute().getMessages().getMessages();
       if (!messages.isEmpty()){
           try {
               ts = vk.messages().getLongPollServer(actor).execute().getTs();
           }catch (ClientException e){
               e.printStackTrace();
           }

       }
       if (!messages.isEmpty() && !messages.get(0).isOut()){
           int messageId = messages.get(0).getId();
           if(messageId> maxMessageId)
               maxMessageId = messageId;
           return messages.get(0);
       }
       return null;

   }

    public VkApiClient getVk() {
        return vk;
    }

    public void setVk(VkApiClient vk) {
        this.vk = vk;
    }

    public static int getTs() {
        return ts;
    }

    public static void setTs(int ts) {
        bot.ts = ts;
    }

    public GroupActor getActor() {
        return actor;
    }

    public void setActor(GroupActor actor) {
        this.actor = actor;
    }

    public static int getMaxMessageId() {
        return maxMessageId;
    }

    public static void setMaxMessageId(int maxMessageId) {
        bot.maxMessageId = maxMessageId;
    }
}
