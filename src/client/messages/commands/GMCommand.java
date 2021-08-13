package client.messages.commands;


import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import constants.ServerConstants;
import handling.channel.ChannelServer;
import handling.world.World;
import server.MapleInventoryManipulator;
import server.maps.MapleMap;
import tools.ArrayMap;
import tools.DateUtil;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.StringUtil;

import java.util.Map;

public class GMCommand
{
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.GM;
    }
    
    public static class 拉 extends WarpHere
    {
    }
    
    public static class 等级 extends Level
    {
    }
    
    public static class 转职 extends Job
    {
    }
    
    public static class 清空 extends ClearInv
    {
    }
    
    public static class 踢人 extends DC
    {
    }
    
    public static class 读取玩家 extends spy
    {
    }
    
    public static class 解除封号 extends UnBan
    {
    }
    
    public static class 刷钱 extends GainMeso
    {
    }
    public static class 给点卷 extends GainCash {
    }
    
    public static class WarpHere extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
                victim.changeMap(c.getPlayer().getMap(), c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition()));
            }
            else {
                final int ch = World.Find.findChannel(splitted[1]);
                if (ch < 0) {
                    c.getPlayer().dropMessage(5, "角色不在线");
                    return 1;
                }
                victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                c.getPlayer().dropMessage(5, "正在传送玩家到身边");
                victim.dropMessage(5, "GM正在传送你");
                if (victim.getMapId() != c.getPlayer().getMapId()) {
                    final MapleMap mapp = victim.getClient().getChannelServer().getMapFactory().getMap(c.getPlayer().getMapId());
                    victim.changeMap(mapp, mapp.getPortal(0));
                }
                victim.changeChannel(c.getChannel());
            }
            return 1;
        }
    }
    
    public static class UnBan extends CommandExecute
    {
        protected boolean hellban;
        
        public UnBan() {
            this.hellban = false;
        }
        
        private String getCommand() {
            return "UnBan";
        }
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "[Syntax] !" + this.getCommand() + " <原因>");
                return 0;
            }
            byte ret;
            if (this.hellban) {
                ret = MapleClient.unHellban(splitted[1]);
            }
            else {
                ret = MapleClient.unban(splitted[1]);
            }
            if (ret == -2) {
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] SQL error.");
                return 0;
            }
            if (ret == -1) {
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] The character does not exist.");
                return 0;
            }
            c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] Successfully unbanned!");
            final byte ret_ = MapleClient.unbanIPMacs(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[UnbanIP] SQL error.");
            }
            else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[UnbanIP] The character does not exist.");
            }
            else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[UnbanIP] No IP or Mac with that character exists!");
            }
            else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[UnbanIP] IP/Mac -- one of them was found and unbanned.");
            }
            else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[UnbanIP] Both IP and Macs were unbanned.");
            }
            return (ret_ > 0) ? 1 : 0;
        }
    }
    
    public static class DC extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            ChannelServer.forceRemovePlayerByCharName(splitted[1]);
            c.getPlayer().dropMessage("解除卡号卡角成功");
            return 1;
        }
    }
    
    public static class Job extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().changeJob(Integer.parseInt(splitted[1]));
            return 1;
        }
    }
    
    public static class GainMeso extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().gainMeso(Integer.MAX_VALUE - c.getPlayer().getMeso(), true);
            return 1;
        }
    }
    
    public static class Level extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setLevel(Short.parseShort(splitted[1]));
            c.getPlayer().levelUp();
            if (c.getPlayer().getExp() < 0) {
                c.getPlayer().gainExp(-c.getPlayer().getExp(), false, false, true);
            }
            return 1;
        }
    }
    
    public static class spy extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "使用规则: !spy <玩家名字>");
            }
            else {
                final MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
                if (victim.getGMLevel() > c.getPlayer().getGMLevel() && c.getPlayer().getId() != victim.getId()) {
                    c.getPlayer().dropMessage(5, "你不能查看比你高权限的人!");
                    return 0;
                }
                if (victim != null) {
                    c.getPlayer().dropMessage(5, "此玩家(" + victim.getId() + ")状态:");
                    c.getPlayer().dropMessage(5, "等級: " + victim.getLevel() + "职业: " + victim.getJob() + "名声: " + victim.getFame());
                    c.getPlayer().dropMessage(5, "地图: " + victim.getMapId() + " - " + victim.getMap().getMapName());
                    c.getPlayer().dropMessage(5, "力量: " + victim.getStat().getStr() + "  ||  敏捷: " + victim.getStat().getDex() + "  ||  智力: " + victim.getStat().getInt() + "  ||  运气: " + victim.getStat().getLuk());
                    c.getPlayer().dropMessage(5, "拥有 " + victim.getMeso() + " 金币.");
                }
                else {
                    c.getPlayer().dropMessage(5, "找不到此玩家.");
                }
            }
            return 1;
        }
    }
    
    public static class ClearInv extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final Map<Pair<Short, Short>, MapleInventoryType> eqs = new ArrayMap<Pair<Short, Short>, MapleInventoryType>();
            final String s = splitted[1];
            switch (s) {
                case "全部": {
                    for (final MapleInventoryType type : MapleInventoryType.values()) {
                        for (final IItem item : c.getPlayer().getInventory(type)) {
                            eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), type);
                        }
                    }
                    break;
                }
                case "已装备道具": {
                    for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)) {
                        eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.EQUIPPED);
                    }
                    break;
                }
                case "武器": {
                    for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIP)) {
                        eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.EQUIP);
                    }
                    break;
                }
                case "消耗": {
                    for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.USE)) {
                        eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.USE);
                    }
                    break;
                }
                case "装饰": {
                    for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.SETUP)) {
                        eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.SETUP);
                    }
                    break;
                }
                case "其他": {
                    for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.ETC)) {
                        eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.ETC);
                    }
                    break;
                }
                case "特殊": {
                    for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.CASH)) {
                        eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.CASH);
                    }
                    break;
                }
                default: {
                    c.getPlayer().dropMessage(6, "[全部/已装备道具/武器/消耗/装饰/其他/特殊]");
                    break;
                }
            }
            for (final Map.Entry<Pair<Short, Short>, MapleInventoryType> eq : eqs.entrySet()) {
                MapleInventoryManipulator.removeFromSlot(c, eq.getValue(), eq.getKey().left, eq.getKey().right, false, false);
            }
            return 1;
        }
    }

    public static class ExpRate extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setExpRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setExpRate(rate);
                }
                c.getPlayer().dropMessage(6, "经验倍率已改变更为 " + rate + "x");
                return 1;
            }
            return 0;
        }

        public String getMessage() {
            return new StringBuilder().append("!exprate <倍率> - 更改经验倍率").toString();
        }
    }


    public static class GainCash extends CommandExecute {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            MapleCharacter player = c.getPlayer();
            int amount = 0;
            String name = "";
            try {
                amount = Integer.parseInt(splitted[1]);
                name = splitted[2];
            } catch (NumberFormatException ex) {
                c.getPlayer().dropMessage("该玩家不在线");
                return 1;
            }
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage("该玩家不在线");
                return 1;
            }
            player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (player == null) {
                c.getPlayer().dropMessage("该玩家不在线");
                return 1;
            }
            player.modifyCSPoints(1, amount, true);
            player.dropMessage("已经收到点卷" + amount + "点");
            final String msg = "GM " + c.getPlayer().getName() + " 給了 " + player.getName() + " 点卷 " + amount + "点";
            for (final ChannelServer cserv2 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch2 : cserv2.getPlayerStorage().getAllCharacters()) {
                    mch2.startMapEffect(msg, 5121009);
                }
            }
            FileoutputUtil.logToFile("logs/Data/给予点卷_"+ DateUtil.getCurrentDateStr2()+"_a.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " 给了 " + player.getName() + " 点卷 " + amount + "点");
            return 1;
        }

        public String getMessage() {
            return new StringBuilder().append("!gaingash <數量> <玩家> - 取得Gash点数").toString();
        }
    }

    public static class CheckGash extends CommandExecute {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须在线");
                return 0;
            }
            final MapleCharacter chrs = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (chrs == null) {
                c.getPlayer().dropMessage(5, "找不到该角色");
            } else {
                c.getPlayer().dropMessage(6, chrs.getName() + " 有 " + chrs.getCSPoints(1) + " 点数.");
            }
            return 1;
        }

        public String getMessage() {
            return new StringBuilder().append("!checkgash <玩家名称> - 检查点数").toString();
        }
    }


    public static class Say extends CommandExecute {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()).getBytes());
                return 1;
            }
            return 0;
        }

        public String getMessage() {
            return new StringBuilder().append("!say 讯息 - 服务器公告").toString();
        }
    }

}
