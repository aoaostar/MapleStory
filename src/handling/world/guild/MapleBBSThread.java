package handling.world.guild;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MapleBBSThread implements Serializable
{
    public static long serialVersionUID;
    public String name;
    public String text;
    public long timestamp;
    public int localthreadID;
    public int guildID;
    public int ownerID;
    public int icon;
    public Map<Integer, MapleBBSReply> replies;
    
    public MapleBBSThread(final int localthreadID, final String name, final String text, final long timestamp, final int guildID, final int ownerID, final int icon) {
        this.replies = new HashMap<Integer, MapleBBSReply>();
        this.localthreadID = localthreadID;
        this.name = name;
        this.text = text;
        this.timestamp = timestamp;
        this.guildID = guildID;
        this.ownerID = ownerID;
        this.icon = icon;
    }
    
    public int getReplyCount() {
        return this.replies.size();
    }
    
    public boolean isNotice() {
        return this.localthreadID == 0;
    }
    
    static {
        MapleBBSThread.serialVersionUID = 3565477792085301248L;
    }
    
    public static class MapleBBSReply implements Serializable
    {
        public int replyid;
        public int ownerID;
        public long timestamp;
        public String content;
        
        public MapleBBSReply(final int replyid, final int ownerID, final String content, final long timestamp) {
            this.ownerID = ownerID;
            this.replyid = replyid;
            this.content = content;
            this.timestamp = timestamp;
        }
    }
    
    public static class ThreadComparator implements Comparator<MapleBBSThread>, Serializable
    {
        @Override
        public int compare(final MapleBBSThread o1, final MapleBBSThread o2) {
            if (o1.localthreadID < o2.localthreadID) {
                return 1;
            }
            if (o1.localthreadID == o2.localthreadID) {
                return 0;
            }
            return -1;
        }
    }
}
