package server.maps;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import tools.Pair;
import tools.StringUtil;

public class MapleReactorFactory
{
    private static final MapleDataProvider data;
    private static final Map<Integer, MapleReactorStats> reactorStats;
    
    public static MapleReactorStats getReactor(final int rid) {
        MapleReactorStats stats = MapleReactorFactory.reactorStats.get(rid);
        if (stats == null) {
            int infoId = rid;
            MapleData reactorData = MapleReactorFactory.data.getData(StringUtil.getLeftPaddedStr(Integer.toString(infoId) + ".img", '0', 11));
            final MapleData link = reactorData.getChildByPath("info/link");
            if (link != null) {
                infoId = MapleDataTool.getIntConvert("info/link", reactorData);
                stats = MapleReactorFactory.reactorStats.get(infoId);
            }
            if (stats == null) {
                stats = new MapleReactorStats();
                reactorData = MapleReactorFactory.data.getData(StringUtil.getLeftPaddedStr(Integer.toString(infoId) + ".img", '0', 11));
                if (reactorData == null) {
                    return stats;
                }
                final boolean canTouch = MapleDataTool.getInt("info/activateByTouch", reactorData, 0) > 0;
                boolean areaSet = false;
                boolean foundState = false;
                byte i = 0;
                while (true) {
                    final MapleData reactorD = reactorData.getChildByPath(String.valueOf(i));
                    if (reactorD == null) {
                        break;
                    }
                    final MapleData reactorInfoData_ = reactorD.getChildByPath("event");
                    if (reactorInfoData_ != null && reactorInfoData_.getChildByPath("0") != null) {
                        final MapleData reactorInfoData = reactorInfoData_.getChildByPath("0");
                        Pair<Integer, Integer> reactItem = null;
                        final int type = MapleDataTool.getIntConvert("type", reactorInfoData);
                        if (type == 100) {
                            reactItem = new Pair<Integer, Integer>(MapleDataTool.getIntConvert("0", reactorInfoData), MapleDataTool.getIntConvert("1", reactorInfoData, 1));
                            if (!areaSet) {
                                stats.setTL(MapleDataTool.getPoint("lt", reactorInfoData));
                                stats.setBR(MapleDataTool.getPoint("rb", reactorInfoData));
                                areaSet = true;
                            }
                        }
                        foundState = true;
                        stats.addState(i, type, reactItem, (byte)MapleDataTool.getIntConvert("state", reactorInfoData), MapleDataTool.getIntConvert("timeOut", reactorInfoData_, -1), (byte)((MapleDataTool.getIntConvert("2", reactorInfoData, 0) > 0 || reactorInfoData.getChildByPath("clickArea") != null || type == 9) ? 1 : (canTouch ? 2 : 0)));
                    }
                    else {
                        stats.addState(i, 999, null, (byte)(foundState ? -1 : (i + 1)), 0, (byte)0);
                    }
                    ++i;
                }
                MapleReactorFactory.reactorStats.put(infoId, stats);
                if (rid != infoId) {
                    MapleReactorFactory.reactorStats.put(rid, stats);
                }
            }
            else {
                MapleReactorFactory.reactorStats.put(rid, stats);
            }
        }
        return stats;
    }
    
    static {
        data = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("wzPath") + "/Reactor.wz"));
        reactorStats = new HashMap<Integer, MapleReactorStats>();
    }
}
