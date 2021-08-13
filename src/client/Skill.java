package client;

import constants.GameConstants;
import java.util.ArrayList;
import java.util.List;
import provider.MapleData;
import provider.MapleDataTool;
import server.MapleStatEffect;
import server.life.Element;

public class Skill implements ISkill
{
    private String name;
    private final List<MapleStatEffect> effects;
    private Element element;
    private byte level;
    private final int id;
    private int animationTime;
    private int requiredSkill;
    private int masterLevel;
    private boolean action;
    private boolean invisible;
    private boolean chargeskill;
    private boolean timeLimited;
    
    public static Skill loadFromData(final int id, final MapleData data) {
        final Skill ret = new Skill(id);
        boolean isBuff = false;
        final int skillType = MapleDataTool.getInt("skillType", data, -1);
        final String elem = MapleDataTool.getString("elemAttr", data, null);
        if (elem != null) {
            ret.element = Element.getFromChar(elem.charAt(0));
        }
        else {
            ret.element = Element.NEUTRAL;
        }
        ret.invisible = (MapleDataTool.getInt("invisible", data, 0) > 0);
        ret.timeLimited = (MapleDataTool.getInt("timeLimited", data, 0) > 0);
        ret.masterLevel = MapleDataTool.getInt("masterLevel", data, 0);
        final MapleData effect = data.getChildByPath("effect");
        if (skillType != -1) {
            if (skillType == 2) {
                isBuff = true;
            }
        }
        else {
            final MapleData action_ = data.getChildByPath("action");
            final MapleData hit = data.getChildByPath("hit");
            final MapleData ball = data.getChildByPath("ball");
            boolean action = false;
            if (action_ == null) {
                if (data.getChildByPath("prepare/action") != null) {
                    action = true;
                }
                else {
                    switch (id) {
                        case 3101005:
                        case 4221001:
                        case 5201001:
                        case 5221009: {
                            action = true;
                            break;
                        }
                    }
                }
            }
            else {
                action = true;
            }
            ret.action = action;
            isBuff = (effect != null && hit == null && ball == null);
            isBuff |= (action_ != null && MapleDataTool.getString("0", action_, "").equals("alert2"));
            switch (id) {
                case 2111002:
                case 2111003:
                case 2121001:
                case 2221001:
                case 2301002:
                case 2321001:
                case 4211001:
                case 12111005:
                case 21000000:
                case 21120006: {
                    isBuff = false;
                    break;
                }
                case 1004:
                case 1017:
                case 1111002:
                case 1111007:
                case 1211009:
                case 1311007:
                case 1320009:
                case 4111001:
                case 4211003:
                case 5001005:
                case 5110001:
                case 5111005:
                case 5121003:
                case 5121009:
                case 5211001:
                case 5211002:
                case 5211006:
                case 5220002:
                case 5220011:
                case 9001004:
                case 10001004:
                case 10001019:
                case 13111005:
                case 15001003:
                case 15100004:
                case 15101006:
                case 15111002:
                case 15111005:
                case 15111006:
                case 20001004:
                case 20001019:
                case 21101003: {
                    isBuff = true;
                    break;
                }
            }
        }
        ret.chargeskill = (data.getChildByPath("keydown") != null);
        for (final MapleData level : data.getChildByPath("level")) {
            ret.effects.add(MapleStatEffect.loadSkillEffectFromData(level, id, isBuff, Byte.parseByte(level.getName())));
        }
        final MapleData reqDataRoot = data.getChildByPath("req");
        if (reqDataRoot != null) {
            for (final MapleData reqData : reqDataRoot.getChildren()) {
                ret.requiredSkill = Integer.parseInt(reqData.getName());
                ret.level = (byte)MapleDataTool.getInt(reqData, 1);
            }
        }
        ret.animationTime = 0;
        if (effect != null) {
            for (final MapleData effectEntry : effect) {
                final Skill skill = ret;
                skill.animationTime += MapleDataTool.getIntConvert("delay", effectEntry, 0);
            }
        }
        return ret;
    }
    
    public Skill(final int id) {
        this.name = "";
        this.effects = new ArrayList<MapleStatEffect>();
        this.id = id;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public MapleStatEffect getEffect(final int level) {
        if (this.effects.size() < level) {
            if (this.effects.size() > 0) {
                return this.effects.get(this.effects.size() - 1);
            }
            return null;
        }
        else {
            if (level <= 0) {
                return this.effects.get(0);
            }
            return this.effects.get(level - 1);
        }
    }
    
    @Override
    public boolean getAction() {
        return this.action;
    }
    
    @Override
    public boolean isChargeSkill() {
        return this.chargeskill;
    }
    
    @Override
    public boolean isInvisible() {
        return this.invisible;
    }
    
    @Override
    public boolean hasRequiredSkill() {
        return this.level > 0;
    }
    
    @Override
    public int getRequiredSkillLevel() {
        return this.level;
    }
    
    @Override
    public int getRequiredSkillId() {
        return this.requiredSkill;
    }
    
    @Override
    public byte getMaxLevel() {
        return (byte)this.effects.size();
    }
    
    @Override
    public boolean canBeLearnedBy(final int job) {
        final int jid = job;
        final int skillForJob = this.id / 10000;
        return (skillForJob == 2001 && GameConstants.isEvan(job)) || (jid / 100 == skillForJob / 100 && jid / 1000 == skillForJob / 1000 && (!GameConstants.isAdventurer(skillForJob) || GameConstants.isAdventurer(job)) && (!GameConstants.isKOC(skillForJob) || GameConstants.isKOC(job)) && (!GameConstants.isAran(skillForJob) || GameConstants.isAran(job)) && (!GameConstants.isEvan(skillForJob) || GameConstants.isEvan(job)) && (!GameConstants.isResist(skillForJob) || GameConstants.isResist(job)) && skillForJob / 10 % 10 <= jid / 10 % 10 && skillForJob % 10 <= jid % 10);
    }
    
    @Override
    public boolean isTimeLimited() {
        return this.timeLimited;
    }
    
    @Override
    public boolean isFourthJob() {
        if (this.id / 10000 >= 2212 && this.id / 10000 < 3000) {
            return this.id / 10000 % 10 >= 7;
        }
        if (this.id / 10000 >= 430 && this.id / 10000 <= 434) {
            return this.id / 10000 % 10 == 4 || this.getMasterLevel() > 0;
        }
        return this.id / 10000 % 10 == 2;
    }
    
    @Override
    public Element getElement() {
        return this.element;
    }
    
    @Override
    public int getAnimationTime() {
        return this.animationTime;
    }
    
    @Override
    public int getMasterLevel() {
        return this.masterLevel;
    }
    
    @Override
    public boolean isBeginnerSkill() {
        final int jobId = this.id / 10000;
        return jobId == 0 || jobId == 1000 || jobId == 2000 || jobId == 2001 || jobId == 3000;
    }
}
