package server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;

public class PredictCardFactory
{
    private static final PredictCardFactory instance;
    protected MapleDataProvider etcData;
    protected Map<Integer, PredictCard> predictCard;
    protected Map<Integer, PredictCardComment> predictCardComment;
    
    public static PredictCardFactory getInstance() {
        return PredictCardFactory.instance;
    }
    
    public PredictCardFactory() {
        this.etcData = MapleDataProviderFactory.getDataProvider(new File("wz/Etc.wz"));
        this.predictCard = new HashMap<Integer, PredictCard>();
        this.predictCardComment = new HashMap<Integer, PredictCardComment>();
    }
    
    public void initialize() {
        if (!this.predictCard.isEmpty() || !this.predictCardComment.isEmpty()) {
            return;
        }
        final MapleData infoData = this.etcData.getData("PredictCard.img");
        for (final MapleData cardDat : infoData) {
            if (cardDat.getName().equals("comment")) {
                continue;
            }
            final PredictCard card = new PredictCard();
            card.name = MapleDataTool.getString("name", cardDat, "");
            card.comment = MapleDataTool.getString("comment", cardDat, "");
            this.predictCard.put(Integer.parseInt(cardDat.getName()), card);
        }
        final MapleData commentData = infoData.getChildByPath("comment");
        for (final MapleData commentDat : commentData) {
            final PredictCardComment comment = new PredictCardComment();
            comment.worldmsg0 = MapleDataTool.getString("0", commentDat, "");
            comment.worldmsg1 = MapleDataTool.getString("1", commentDat, "");
            comment.score = MapleDataTool.getIntConvert("score", commentDat, 0);
            comment.effectType = MapleDataTool.getIntConvert("effectType", commentDat, 0);
            this.predictCardComment.put(Integer.parseInt(commentDat.getName()), comment);
        }
    }
    
    public PredictCard getPredictCard(final int id) {
        if (!this.predictCard.containsKey(id)) {
            return null;
        }
        return this.predictCard.get(id);
    }
    
    public PredictCardComment getPredictCardComment(final int id) {
        if (!this.predictCardComment.containsKey(id)) {
            return null;
        }
        return this.predictCardComment.get(id);
    }
    
    public PredictCardComment RandomCardComment() {
        return this.getPredictCardComment(Randomizer.nextInt(this.predictCardComment.size()));
    }
    
    public int getCardCommentSize() {
        return this.predictCardComment.size();
    }
    
    static {
        instance = new PredictCardFactory();
    }
    
    public static class PredictCard
    {
        public String name;
        public String comment;
    }
    
    public static class PredictCardComment
    {
        public int score;
        public int effectType;
        public String worldmsg0;
        public String worldmsg1;
    }
}
