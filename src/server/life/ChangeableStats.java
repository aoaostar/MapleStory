package server.life;

import constants.GameConstants;

public class ChangeableStats extends OverrideMonsterStats
{
    public int watk;
    public int matk;
    public int acc;
    public int eva;
    public int PDRate;
    public int MDRate;
    public int pushed;
    public int level;
    
    public ChangeableStats(final MapleMonsterStats stats, final OverrideMonsterStats ostats) {
        this.hp = ostats.getHp();
        this.exp = ostats.getExp();
        this.mp = ostats.getMp();
        this.acc = stats.getAcc();
        this.eva = stats.getEva();
        this.level = stats.getLevel();
    }
    
    public ChangeableStats(final MapleMonsterStats stats, final int newLevel, final boolean pqMob) {
        final double mod = newLevel / stats.getLevel();
        final double pqMod = pqMob ? 2.5 : 1.0;
        this.hp = Math.round((stats.isBoss() ? (stats.getHp() * mod) : GameConstants.getMonsterHP(newLevel)) * pqMod);
        this.exp = (int)Math.round(stats.getExp() * mod * pqMod);
        this.mp = (int)Math.round(stats.getMp() * mod * pqMod);
        this.acc = Math.round((float)(stats.getAcc() + Math.max(0, newLevel - stats.getLevel()) * 2));
        this.eva = Math.round((float)(stats.getEva() + Math.max(0, newLevel - stats.getLevel())));
        this.level = newLevel;
    }
}
