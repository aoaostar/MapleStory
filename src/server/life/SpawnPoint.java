package server.life;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;
import server.MapleCarnivalFactory;
import server.maps.MapleMap;
import server.maps.MapleReactor;
import tools.MaplePacketCreator;

public class SpawnPoint extends Spawns
{
    private final MapleMonster monster;
    private final Point pos;
    private long nextPossibleSpawn;
    private final int mobTime;
    private int carnival;
    private final AtomicInteger spawnedMonsters;
    private final boolean immobile;
    private final String msg;
    private final byte carnivalTeam;
    
    public SpawnPoint(final MapleMonster monster, final Point pos, final int mobTime, final byte carnivalTeam, final String msg) {
        this.carnival = -1;
        this.spawnedMonsters = new AtomicInteger(0);
        this.monster = monster;
        this.pos = pos;
        this.mobTime = ((mobTime < 0) ? -1 : (mobTime * 1000));
        this.carnivalTeam = carnivalTeam;
        this.msg = msg;
        this.immobile = !monster.getStats().getMobile();
        this.nextPossibleSpawn = System.currentTimeMillis();
    }
    
    public void setCarnival(final int c) {
        this.carnival = c;
    }
    
    @Override
    public Point getPosition() {
        return this.pos;
    }
    
    @Override
    public MapleMonster getMonster() {
        return this.monster;
    }
    
    @Override
    public byte getCarnivalTeam() {
        return this.carnivalTeam;
    }
    
    @Override
    public int getCarnivalId() {
        return this.carnival;
    }
    
    @Override
    public boolean shouldSpawn() {
        return this.mobTime >= 0 && ((this.mobTime == 0 && !this.immobile) || this.spawnedMonsters.get() <= 0) && this.spawnedMonsters.get() <= 1 && this.nextPossibleSpawn <= System.currentTimeMillis();
    }
    
    @Override
    public MapleMonster spawnMonster(final MapleMap map) {
        final MapleMonster mob = new MapleMonster(this.monster);
        mob.setPosition(this.pos);
        mob.setCarnivalTeam(this.carnivalTeam);
        this.spawnedMonsters.incrementAndGet();
        mob.addListener(new MonsterListener() {
            @Override
            public void monsterKilled() {
                SpawnPoint.this.nextPossibleSpawn = System.currentTimeMillis();
                if (SpawnPoint.this.mobTime > 0) {
                    SpawnPoint.this.nextPossibleSpawn += SpawnPoint.this.mobTime;
                }
                SpawnPoint.this.spawnedMonsters.decrementAndGet();
            }
        });
        map.spawnMonster(mob, -2);
        if (this.carnivalTeam > -1) {
            for (final MapleReactor r : map.getAllReactorsThreadsafe()) {
                if (r.getName().startsWith(String.valueOf(this.carnivalTeam)) && r.getReactorId() == 9980000 + this.carnivalTeam && r.getState() < 5) {
                    final int num = Integer.parseInt(r.getName().substring(1, 2));
                    final MapleCarnivalFactory.MCSkill skil = MapleCarnivalFactory.getInstance().getGuardian(num);
                    if (skil == null) {
                        continue;
                    }
                    skil.getSkill().applyEffect(null, mob, false);
                }
            }
        }
        if (this.msg != null) {
            map.broadcastMessage(MaplePacketCreator.serverNotice(6, this.msg));
        }
        return mob;
    }
    
    @Override
    public int getMobTime() {
        return this.mobTime;
    }
}
