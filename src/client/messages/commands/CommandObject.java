package client.messages.commands;


import client.MapleClient;
import constants.ServerConstants;

public class CommandObject
{
    private final String command;
    private final int gmLevelReq;
    private final CommandExecute exe;
    
    public CommandObject(final String com, final CommandExecute c, final int gmLevel) {
        this.command = com;
        this.exe = c;
        this.gmLevelReq = gmLevel;
    }
    
    public int execute(final MapleClient c, final String[] splitted) {
        return this.exe.execute(c, splitted);
    }
    
    public ServerConstants.CommandType getType() {
        return this.exe.getType();
    }
    
    public int getReqGMLevel() {
        return this.gmLevelReq;
    }
}
