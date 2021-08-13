package client;

import server.*;
import server.life.*;

public interface ISkill
{
    int getId();
    
    MapleStatEffect getEffect(final int p0);
    
    byte getMaxLevel();
    
    int getAnimationTime();
    
    boolean canBeLearnedBy(final int p0);
    
    boolean isFourthJob();
    
    boolean getAction();
    
    boolean isTimeLimited();
    
    int getMasterLevel();
    
    Element getElement();
    
    boolean isBeginnerSkill();
    
    boolean hasRequiredSkill();
    
    boolean isInvisible();
    
    boolean isChargeSkill();
    
    int getRequiredSkillLevel();
    
    int getRequiredSkillId();
    
    String getName();
}
