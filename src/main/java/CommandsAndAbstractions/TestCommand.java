package CommandsAndAbstractions;

import VkServices.VkManager;
import com.vk.api.sdk.objects.messages.Message;

public class TestCommand extends Command {
    public TestCommand(String name){
        super(name);
    }

    @Override
    public void exec(Message message) {
       new VkManager().sendMessage("Unknown commad", message.getUserId());
    }
}
