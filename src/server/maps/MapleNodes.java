package server.maps;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tools.Pair;

public class MapleNodes
{
    private final Map<Integer, MapleNodeInfo> nodes;
    private final List<Rectangle> areas;
    private final List<MaplePlatform> platforms;
    private final List<MonsterPoint> monsterPoints;
    private final List<Integer> skillIds;
    private final List<Pair<Integer, Integer>> mobsToSpawn;
    private final List<Pair<Point, Integer>> guardiansToSpawn;
    private int nodeStart;
    private int nodeEnd;
    private final int mapid;
    private boolean firstHighest;
    
    public MapleNodes(final int mapid) {
        this.nodeStart = -1;
        this.nodeEnd = -1;
        this.firstHighest = true;
        this.nodes = new LinkedHashMap<Integer, MapleNodeInfo>();
        this.areas = new ArrayList<Rectangle>();
        this.platforms = new ArrayList<MaplePlatform>();
        this.skillIds = new ArrayList<Integer>();
        this.monsterPoints = new ArrayList<MonsterPoint>();
        this.mobsToSpawn = new ArrayList<Pair<Integer, Integer>>();
        this.guardiansToSpawn = new ArrayList<Pair<Point, Integer>>();
        this.mapid = mapid;
    }
    
    public final void setNodeStart(final int ns) {
        this.nodeStart = ns;
    }
    
    public void setNodeEnd(final int ns) {
        this.nodeEnd = ns;
    }
    
    public void addNode(final MapleNodeInfo mni) {
        this.nodes.put(mni.key, mni);
    }
    
    public Collection<MapleNodeInfo> getNodes() {
        return new ArrayList<MapleNodeInfo>(this.nodes.values());
    }
    
    public MapleNodeInfo getNode(final int index) {
        int i = 1;
        for (final MapleNodeInfo x : this.getNodes()) {
            if (i == index) {
                return x;
            }
            ++i;
        }
        return null;
    }
    
    private int getNextNode(final MapleNodeInfo mni) {
        if (mni == null) {
            return -1;
        }
        this.addNode(mni);
        int ret = -1;
        for (final int i : mni.edge) {
            if (this.nodes.get(i) == null) {
                if (ret != -1 && this.mapid / 100 == 9211204) {
                    if (this.firstHighest) {
                        this.firstHighest = false;
                        ret = Math.max(ret, i);
                        break;
                    }
                    ret = Math.min(ret, i);
                }
                else {
                    ret = i;
                }
            }
        }
        return ret;
    }
    
    public void sortNodes() {
        if (this.nodes.size() <= 0 || this.nodeStart < 0) {
            return;
        }
        final Map<Integer, MapleNodeInfo> unsortedNodes = new HashMap<Integer, MapleNodeInfo>(this.nodes);
        final int nodeSize = unsortedNodes.size();
        this.nodes.clear();
        for (int nextNode = this.getNextNode(unsortedNodes.get(this.nodeStart)); this.nodes.size() != nodeSize && nextNode >= 0; nextNode = this.getNextNode(unsortedNodes.get(nextNode))) {}
    }
    
    public void addMapleArea(final Rectangle rec) {
        this.areas.add(rec);
    }
    
    public List<Rectangle> getAreas() {
        return new ArrayList<Rectangle>(this.areas);
    }
    
    public Rectangle getArea(final int index) {
        return this.getAreas().get(index);
    }
    
    public void addPlatform(final MaplePlatform mp) {
        this.platforms.add(mp);
    }
    
    public List<MaplePlatform> getPlatforms() {
        return new ArrayList<MaplePlatform>(this.platforms);
    }
    
    public List<MonsterPoint> getMonsterPoints() {
        return this.monsterPoints;
    }
    
    public void addMonsterPoint(final int x, final int y, final int fh, final int cy, final int team) {
        this.monsterPoints.add(new MonsterPoint(x, y, fh, cy, team));
    }
    
    public void addMobSpawn(final int mobId, final int spendCP) {
        this.mobsToSpawn.add(new Pair<Integer, Integer>(mobId, spendCP));
    }
    
    public List<Pair<Integer, Integer>> getMobsToSpawn() {
        return this.mobsToSpawn;
    }
    
    public void addGuardianSpawn(final Point guardian, final int team) {
        this.guardiansToSpawn.add(new Pair<Point, Integer>(guardian, team));
    }
    
    public List<Pair<Point, Integer>> getGuardians() {
        return this.guardiansToSpawn;
    }
    
    public List<Integer> getSkillIds() {
        return this.skillIds;
    }
    
    public void addSkillId(final int z) {
        this.skillIds.add(z);
    }
    
    public static class MapleNodeInfo
    {
        public int node;
        public int key;
        public int x;
        public int y;
        public int attr;
        public List<Integer> edge;
        
        public MapleNodeInfo(final int node, final int key, final int x, final int y, final int attr, final List<Integer> edge) {
            this.node = node;
            this.key = key;
            this.x = x;
            this.y = y;
            this.attr = attr;
            this.edge = edge;
        }
    }
    
    public static class MaplePlatform
    {
        public String name;
        public int start;
        public int speed;
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int r;
        public List<Integer> SN;
        
        public MaplePlatform(final String name, final int start, final int speed, final int x1, final int y1, final int x2, final int y2, final int r, final List<Integer> SN) {
            this.name = name;
            this.start = start;
            this.speed = speed;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.r = r;
            this.SN = SN;
        }
    }
    
    public static class MonsterPoint
    {
        public int x;
        public int y;
        public int fh;
        public int cy;
        public int team;
        
        public MonsterPoint(final int x, final int y, final int fh, final int cy, final int team) {
            this.x = x;
            this.y = y;
            this.fh = fh;
            this.cy = cy;
            this.team = team;
        }
    }
}
