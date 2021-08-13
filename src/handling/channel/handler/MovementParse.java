package handling.channel.handler;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import server.maps.AnimatedMapleMapObject;
import server.movement.AbsoluteLifeMovement;
import server.movement.AranMovement;
import server.movement.BounceMovement;
import server.movement.ChairMovement;
import server.movement.ChangeEquipSpecialAwesome;
import server.movement.JumpDownMovement;
import server.movement.LifeMovement;
import server.movement.LifeMovementFragment;
import server.movement.RelativeLifeMovement;
import server.movement.TeleportMovement;
import tools.data.input.SeekableLittleEndianAccessor;

public class MovementParse
{
    public static final List<LifeMovementFragment> parseMovement(final SeekableLittleEndianAccessor lea, final int kind) {
        final List<LifeMovementFragment> res = new ArrayList<LifeMovementFragment>();
        final byte numCommands = lea.readByte();
        String 类型 = "";
        switch (kind) {
            case 1: {
                类型 = "角色移动";
                break;
            }
            case 2: {
                类型 = "怪物移动";
                break;
            }
            case 3: {
                类型 = "宠物移动";
                break;
            }
            case 4: {
                类型 = "召唤兽移动";
                break;
            }
            case 5: {
                类型 = "龙移动";
                break;
            }
        }
        for (byte i = 0; i < numCommands; ++i) {
            final byte command = lea.readByte();
            switch (command) {
                case -1: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short unk = lea.readShort();
                    final short fh = lea.readShort();
                    final byte newstate = lea.readByte();
                    final short duration = lea.readShort();
                    final BounceMovement bm = new BounceMovement(command, new Point(xpos, ypos), duration, newstate);
                    bm.setFH(fh);
                    bm.setUnk(unk);
                    res.add(bm);
                    break;
                }
                case 0:
                case 5:
                case 17: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short xwobble = lea.readShort();
                    final short ywobble = lea.readShort();
                    final short unk2 = lea.readShort();
                    final byte newstate2 = lea.readByte();
                    final short duration2 = lea.readShort();
                    final AbsoluteLifeMovement alm = new AbsoluteLifeMovement(command, new Point(xpos, ypos), duration2, newstate2);
                    alm.setUnk(unk2);
                    alm.setPixelsPerSecond(new Point(xwobble, ywobble));
                    res.add(alm);
                    break;
                }
                case 1:
                case 2:
                case 6:
                case 12:
                case 13:
                case 16:
                case 18:
                case 19:
                case 22:
                case 23:
                case 24: {
                    final short xmod = lea.readShort();
                    final short ymod = lea.readShort();
                    final byte newstate3 = lea.readByte();
                    final short duration3 = lea.readShort();
                    final RelativeLifeMovement rlm = new RelativeLifeMovement(command, new Point(xmod, ymod), duration3, newstate3);
                    res.add(rlm);
                    break;
                }
                case 3:
                case 4:
                case 7:
                case 8:
                case 9:
                case 14: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short xwobble = lea.readShort();
                    final short ywobble = lea.readShort();
                    final byte newstate = lea.readByte();
                    final TeleportMovement tm = new TeleportMovement(command, new Point(xpos, ypos), newstate);
                    tm.setPixelsPerSecond(new Point(xwobble, ywobble));
                    res.add(tm);
                    break;
                }
                case 10: {
                    res.add(new ChangeEquipSpecialAwesome(lea.readByte()));
                    break;
                }
                case 11: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short unk = lea.readShort();
                    final byte newstate4 = lea.readByte();
                    final short duration4 = lea.readShort();
                    final ChairMovement cm = new ChairMovement(command, new Point(xpos, ypos), duration4, newstate4);
                    cm.setUnk(unk);
                    res.add(cm);
                    break;
                }
                case 15: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short xwobble = lea.readShort();
                    final short ywobble = lea.readShort();
                    final short unk2 = lea.readShort();
                    final short fh2 = lea.readShort();
                    final byte newstate5 = lea.readByte();
                    final short duration5 = lea.readShort();
                    final JumpDownMovement jdm = new JumpDownMovement(command, new Point(xpos, ypos), duration5, newstate5);
                    jdm.setUnk(unk2);
                    jdm.setPixelsPerSecond(new Point(xwobble, ywobble));
                    jdm.setFH(fh2);
                    res.add(jdm);
                    break;
                }
                case 20:
                case 21: {
                    final short unk3 = lea.readShort();
                    final byte newstate6 = lea.readByte();
                    final AranMovement acm = new AranMovement(command, new Point(0, 0), unk3, newstate6);
                    res.add(acm);
                    break;
                }
                default: {
                    System.out.println("Kind movement: " + 类型 + ", Remaining : " + (numCommands - res.size()) + " New type of movement ID : " + command + ", packet : " + lea.toString(true));
                    return null;
                }
            }
        }
        if (numCommands != res.size()) {
            System.out.println("error in movement");
            return null;
        }
        return res;
    }
    
    public static final void updatePosition(final List<LifeMovementFragment> movement, final AnimatedMapleMapObject target, final int yoffset) {
        for (final LifeMovementFragment move : movement) {
            if (move instanceof LifeMovement) {
                if (move instanceof AbsoluteLifeMovement) {
                    final Point position2;
                    final Point position = position2 = (move).getPosition();
                    position2.y += yoffset;
                    target.setPosition(position);
                }
                target.setStance(((LifeMovement)move).getNewstate());
            }
        }
    }
}
