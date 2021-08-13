package server.life;

import server.maps.*;
import java.awt.*;

public abstract class Spawns
{
    public abstract MapleMonster getMonster();
    
    public abstract byte getCarnivalTeam();
    
    public abstract boolean shouldSpawn();
    
    public abstract int getCarnivalId();
    
    public abstract MapleMonster spawnMonster(final MapleMap p0);
    
    public abstract int getMobTime();
    
    public abstract Point getPosition();
}
