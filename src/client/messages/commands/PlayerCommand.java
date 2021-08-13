package client.messages.commands;

import client.messages.CommandProcessorUtil;
import constants.*;
import client.*;
import scripting.*;
import server.maps.*;
import server.life.*;
import java.util.*;
import tools.*;
import handling.world.*;

public class PlayerCommand
{
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.NORMAL;
    }
    
    public static class 帮助 extends help
    {
    }
    
    public static class 自由 extends zy
    {
    }
    
    public static class 怪物 extends Mob
    {
    }
    
    public static class 万能 extends wn
    {
    }
    
    public static class 爆率 extends Mobdrop
    {
    }
    
    public static class ea extends 查看
    {
    }
    
    public static class 解卡 extends 查看
    {
    }
    
    public static class 破攻 extends pg
    {
    }
    
    public static class 查看 extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().dropMessage(1, "假死已处理完毕.");
            c.getPlayer().dropMessage(6, "当前时间是" + FileoutputUtil.CurrentReadable_Time() + " GMT+8 | 经验值倍率 " + (int)(c.getPlayer().getEXPMod() * 100 * (c.getPlayer().getStat().expBuff / 100.0)) + "%, 怪物倍率 " + (int)(c.getPlayer().getDropMod() * 100 * (c.getPlayer().getStat().dropBuff / 100.0)) + "%, 金币倍率 " + (int)(c.getPlayer().getStat().mesoBuff / 100.0 * 100.0) + "%");
            c.getPlayer().dropMessage(6, "当前延迟 " + c.getPlayer().getClient().getLatency() + " 毫秒");
            if (c.getPlayer().isAdmin()) {
                c.sendPacket(MaplePacketCreator.sendPyramidEnergy("massacre_hit", String.valueOf(50)));
            }
            return 1;
        }
    }
    
    public static class zy extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            final NPCScriptManager npc = NPCScriptManager.getInstance();
            npc.start(c, 9900007, 86);
            if (c.getPlayer().getBossLog("狮熊Boss") > 0) {
                c.getPlayer().resetBossLog("狮熊Boss");
            }
            if (c.getPlayer().getBossLog("普通黑龙") > 0) {
                c.getPlayer().resetBossLog("普通黑龙");
            }
            if (c.getPlayer().getBossLog("树精Boss") > 0) {
                c.getPlayer().resetBossLog("树精Boss");
            }
            if (c.getPlayer().getBossLog("普通扎昆") > 0) {
                c.getPlayer().resetBossLog("普通扎昆");
            }
            return 1;
        }
    }
    
    public static class wn extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            final NPCScriptManager npc = NPCScriptManager.getInstance();
            npc.start(c, 9900004, 0);
            return 1;
        }
    }
    
    public static class Mobdrop extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            final NPCScriptManager npc = NPCScriptManager.getInstance();
            npc.start(c, 2000);
            return 1;
        }
    }
    
    public static class Mob extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleMonster mob = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000.0, Arrays.asList(MapleMapObjectType.MONSTER))) {
                mob = (MapleMonster)monstermo;
                if (mob.isAlive()) {
                    c.getPlayer().dropMessage(6, "怪物: " + mob.toString());
                    break;
                }
            }
            if (mob == null) {
                c.getPlayer().dropMessage(6, "查看失败: 1.没有找到需要查看的怪物信息. 2.你周围没有怪物出现. 3.有些怪物禁止查看.");
            }
            return 1;
        }
    }
    
    public static class CGM extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted[1] == null) {
                c.getPlayer().dropMessage(6, "请打字谢谢.");
                return 1;
            }
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage(6, "因为你自己是GM无法使用此命令,可以尝试!cngm <讯息> 來建立GM聊天頻道~");
                return 1;
            }
            if (!c.getPlayer().getCheatTracker().GMSpam(100000, 1)) {
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "頻道 " + c.getPlayer().getClient().getChannel() + " 玩家 [" + c.getPlayer().getName() + "] : " + StringUtil.joinStringFrom(splitted, 1)).getBytes());
                c.getPlayer().dropMessage(6, "讯息已经发给GM了!");
            }
            else {
                c.getPlayer().dropMessage(6, "为了防止对GM刷屏所以每1分鐘只能发一次.");
            }
            return 1;
        }
    }
    
    public static class pg extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final int VipCount = c.getPlayer().getVip();
            long maxdamage = 199999 + VipCount * 10000;
            if (maxdamage >= 2147483647L || maxdamage < 0L) {
                maxdamage = 2147483647L;
            }
            c.getPlayer().refreshPGDamage();
            final String mds = "您当前的伤害上限为：" + maxdamage + "   当前破攻伤害为：" + c.getPlayer().curPGDamage;
            c.getPlayer().dropMessage(5, "伤害上限计算公式： 基础伤害(199999) + 您的破功等级*10000 ");
            c.getPlayer().dropMessage(-1, mds);
            c.getPlayer().dropMessage(5, mds);
            return 1;
        }
    }
    
    public static class help extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(5, "指令列表 :");
            c.getPlayer().dropMessage(5, "@解卡/@查看/@ea  <解除异常+查看当前状态>");
            c.getPlayer().dropMessage(5, "@爆率 爆率       <查询当前地图怪物爆率>");
            c.getPlayer().dropMessage(5, "@自由/@zy     < 立即回到自于市场 >");
            c.getPlayer().dropMessage(5, "@万能/@wn        < 打开多功能NPC >");
            c.getPlayer().dropMessage(5, "@怪物/@Mob  <查看身边怪物信息/血量>");
            c.getPlayer().dropMessage(5, "@pg           查看自己的破攻上限(也可以使用 @破攻 )");
            c.getPlayer().dropMessage(5, "@abc   召唤怪物\n(蜗牛、黑木妖、火独眼兽、小石球、海胆、鲨鱼、骷髅龙、小铜人、银人、小金人)\n最高可召唤100只 每天召唤10次");
            return 1;
        }
    }

    public static class abc extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if(c.getPlayer().getVipexpired() < System.currentTimeMillis()) {
                c.getPlayer().dropMessage("你不是VIP玩家或者VIP过期，请联系管理员");
                return 0;
            }
            if (splitted.length < 2) {
                return 0;
            }
            String name = splitted[1];
            int mid = 0;
            if("小金人".equals(name)) {
                mid = 9600019;
            }else if("银人".equals(name)) {
                mid = 9600024;
            }else if("小铜人".equals(name)) {
                mid = 9600020;
            }else if("骷髅龙".equals(name)) {
                mid = 8190003;
            }else if("鲨鱼".equals(name)) {
                mid = 8150100;
            }else if("海胆".equals(name)) {
                mid = 2230108;
            }else if("小石球".equals(name)) {
                mid = 5200000;
            }else if("火独眼兽".equals(name)) {
                mid = 2230100;
            }else if("黑木妖".equals(name)) {
                mid = 1110101;
            }else if("蜗牛".equals(name)){
                mid = 100100;
            } else{
                c.getPlayer().dropMessage("暂不支持召唤: "+name);
                return 0;
            }
            if(c.getPlayer().getBossLog(name) > 9) {
                c.getPlayer().dropMessage(6,"今日召唤已超过10次，请明天再来吧");
                return 0;
            }
            c.getPlayer().setBossLog(name);
            int num = Math.min(CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1), 500);
            if (num > 100) {
                num = 100;
            }
            for (int i = 0; i < num; ++i) {
                final MapleMonster mob = MapleLifeFactory.getMonster(mid);
                c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());
            }
            return 1;
        }
        public String getMessage() {
            return new StringBuilder().append("!abc <怪物ID>  - 召唤怪物").toString();
        }
    }
}
