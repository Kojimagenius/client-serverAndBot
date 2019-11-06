package CommandsAndAbstractions;

import VkServices.VkManager;
import com.vk.api.sdk.objects.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Address extends Command {

    private String address;


    public Address(String name, String address) {
        super(name);
        this.address = address;
    }

    public Address(String name) {
        super(name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress() {
        try {
            URL ip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(ip.openStream()));
            this.address = in.readLine();
        }catch (IOException e){
            System.out.println("Error while trying get address");
            e.printStackTrace();
        }
    }

    @Override
    public void exec(Message message) {
        setAddress();
        new VkManager().sendMessage(address, message.getUserId());
    }
}
