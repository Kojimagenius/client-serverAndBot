package VkServices;

import CommandsAndAbstractions.Address;
import CommandsAndAbstractions.Command;
import CommandsAndAbstractions.TestCommand;

import java.util.HashSet;

public class CommandManager {
    private static HashSet<Command> commands = new HashSet<>();
    static {
        commands.add(new TestCommand("unknown"));
        commands.add(new Address("Address"));

    }
    public static HashSet<Command> getCommands(){
        return commands;
    }
    public static void addCommand(Command command){
        commands.add(command);
    }
}
