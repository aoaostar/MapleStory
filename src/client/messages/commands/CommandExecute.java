package client.messages.commands;


import client.MapleClient;
import constants.ServerConstants;

public abstract class CommandExecute
{
    public abstract int execute(final MapleClient p0, final String[] p1);
    
    public ServerConstants.CommandType getType() {
        return ServerConstants.CommandType.NORMAL;
    }
    
    public abstract static class TradeExecute extends CommandExecute
    {
        @Override
        public ServerConstants.CommandType getType() {
            return ServerConstants.CommandType.TRADE;
        }
    }
    
    enum ReturnValue
    {
        DONT_LOG, 
        LOG
    }
}
