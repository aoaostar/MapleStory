package client;

import constants.GameConstants;
import database.DatabaseConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import server.MapleItemInformationProvider;
import tools.data.output.MaplePacketLittleEndianWriter;
import tools.packet.MonsterBookPacket;


public class MonsterBook implements Serializable
{
    private static final long serialVersionUID = 7179541993413738569L;
    private boolean changed;
    private int SpecialCard;
    private int NormalCard;
    private int BookLevel;
    private final Map<Integer, Integer> cards;
    
    public static MonsterBook loadCards(final int charid) throws SQLException {
        final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM monsterbook WHERE charid = ? ORDER BY cardid ASC");
        ps.setInt(1, charid);
        final ResultSet rs = ps.executeQuery();
        final Map<Integer, Integer> cards = new LinkedHashMap<Integer, Integer>();
        while (rs.next()) {
            cards.put(rs.getInt("cardid"), rs.getInt("level"));
        }
        rs.close();
        ps.close();
        return new MonsterBook(cards);
    }
    
    public MonsterBook(final Map<Integer, Integer> cards) {
        this.changed = false;
        this.SpecialCard = 0;
        this.NormalCard = 0;
        this.BookLevel = 1;
        this.cards = cards;
        for (final Map.Entry<Integer, Integer> card : cards.entrySet()) {
            if (GameConstants.isSpecialCard(card.getKey())) {
                this.SpecialCard += card.getValue();
            }
            else {
                this.NormalCard += card.getValue();
            }
        }
        this.calculateLevel();
    }
    
    public Map<Integer, Integer> getCards() {
        return this.cards;
    }
    
    public int getTotalCards() {
        return this.SpecialCard + this.NormalCard;
    }
    
    public int getLevelByCard(final int cardid) {
        return (this.cards.get(cardid) == null) ? 0 : this.cards.get(cardid);
    }
    
    public void saveCards(final int charid) throws SQLException {
        if (!this.changed || this.cards.size() == 0) {
            return;
        }
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = con.prepareStatement("SELECT * FROM monsterbook WHERE `charid` = ? AND `cardid` = ? LIMIT 1");
        ps.setInt(1, charid);
        for (final Map.Entry<Integer, Integer> all : this.cards.entrySet()) {
            PreparedStatement ps2 = null;
            final int cardid = all.getKey();
            ps.setInt(2, cardid);
            rs = ps.executeQuery();
            if (rs.next()) {
                ps2 = con.prepareStatement("UPDATE monsterbook SET `level` = ? WHERE `charid` = ? AND `cardid` = ?");
                ps2.setInt(1, all.getValue());
                ps2.setInt(2, charid);
                ps2.setInt(3, cardid);
                ps2.executeUpdate();
                ps2.close();
            }
            else {
                ps2 = con.prepareStatement("INSERT INTO monsterbook (`charid`, `cardid`, `level`) VALUES (?, ?, ?)");
                ps2.setInt(1, charid);
                ps2.setInt(2, cardid);
                ps2.setInt(3, all.getValue());
                ps2.execute();
                ps2.close();
            }
            rs.close();
        }
        ps.close();
    }
    
    private void calculateLevel() {
        final int Size = this.NormalCard + this.SpecialCard;
        this.BookLevel = 8;
        for (int i = 0; i < 8; ++i) {
            if (Size <= GameConstants.getBookLevel(i)) {
                this.BookLevel = i + 1;
                break;
            }
        }
    }
    
    public void addCardPacket(final MaplePacketLittleEndianWriter mplew) {
        mplew.writeShort(this.cards.size());
        for (final Map.Entry<Integer, Integer> all : this.cards.entrySet()) {
            mplew.writeShort(GameConstants.getCardShortId(all.getKey()));
            mplew.write(all.getValue());
        }
    }
    
    public void addCharInfoPacket(final int bookcover, final MaplePacketLittleEndianWriter mplew) {
        mplew.writeInt(this.BookLevel);
        mplew.writeInt(this.NormalCard);
        mplew.writeInt(this.SpecialCard);
        mplew.writeInt(this.NormalCard + this.SpecialCard);
        mplew.writeInt(MapleItemInformationProvider.getInstance().getCardMobId(bookcover));
    }
    
    public void updateCard(final MapleClient c, final int cardid) {
        c.getSession().write(MonsterBookPacket.changeCover(cardid));
    }
    
    public void addCard(final MapleClient c, final int cardid) {
        this.changed = true;
        if (this.cards.containsKey(cardid)) {
            final int levels = this.cards.get(cardid);
            if (levels >= 5) {
                c.getSession().write(MonsterBookPacket.addCard(true, cardid, levels));
            }
            else {
                if (GameConstants.isSpecialCard(cardid)) {
                    ++this.SpecialCard;
                }
                else {
                    ++this.NormalCard;
                }
                c.getSession().write(MonsterBookPacket.addCard(false, cardid, levels));
                c.getSession().write(MonsterBookPacket.showGainCard(cardid));
                this.cards.put(cardid, levels + 1);
                this.calculateLevel();
            }
            return;
        }
        if (GameConstants.isSpecialCard(cardid)) {
            ++this.SpecialCard;
        }
        else {
            ++this.NormalCard;
        }
        this.cards.put(cardid, 1);
        c.getSession().write(MonsterBookPacket.addCard(false, cardid, 1));
        c.getSession().write(MonsterBookPacket.showGainCard(cardid));
        this.calculateLevel();
    }
}
