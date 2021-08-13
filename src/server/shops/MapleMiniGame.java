package server.shops;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import constants.GameConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import server.maps.MapleMapObject;
import server.quest.MapleQuest;
import tools.packet.PlayerShopPacket;

public class MapleMiniGame extends AbstractPlayerStore
{
    private static final int slots = 2;
    private final boolean[] exitAfter;
    private final boolean[] ready;
    private final int[] points;
    private int GameType;
    private int[][] piece;
    private final List<Integer> matchcards;
    int loser;
    int turn;
    int piecetype;
    int firstslot;
    int tie;
    int REDO;
    
    public MapleMiniGame(final MapleCharacter owner, final int itemId, final String description, final String pass, final int GameType) {
        super(owner, itemId, description, pass, 1);
        this.GameType = 0;
        this.piece = new int[15][15];
        this.matchcards = new ArrayList<Integer>();
        this.loser = 0;
        this.turn = 1;
        this.piecetype = 0;
        this.firstslot = 0;
        this.tie = -1;
        this.REDO = -1;
        this.GameType = GameType;
        this.points = new int[2];
        this.exitAfter = new boolean[2];
        this.ready = new boolean[2];
        this.reset();
    }
    
    public void reset() {
        for (int i = 0; i < 2; ++i) {
            this.points[i] = 0;
            this.exitAfter[i] = false;
            this.ready[i] = false;
        }
    }
    
    public void setFirstSlot(final int type) {
        this.firstslot = type;
    }
    
    public int getFirstSlot() {
        return this.firstslot;
    }
    
    public void setPoints(final int slot) {
        final int[] points = this.points;
        ++points[slot];
        this.checkWin();
    }
    
    public int getPoints() {
        int ret = 0;
        for (int i = 0; i < 2; ++i) {
            ret += this.points[i];
        }
        return ret;
    }
    
    public void checkWin() {
        if (this.getPoints() >= this.getMatchesToWin() && !this.isOpen()) {
            int x = 0;
            int highest = 0;
            boolean tie = false;
            boolean REDO = false;
            for (int i = 0; i < 2; ++i) {
                if (this.points[i] > highest) {
                    x = i;
                    highest = this.points[i];
                    tie = false;
                    REDO = false;
                }
                else if (this.points[i] == highest) {
                    tie = true;
                    REDO = true;
                }
                this.points[i] = 0;
            }
            this.broadcastToVisitors(PlayerShopPacket.getMiniGameResult(this, tie ? 1 : 2, x));
            this.setOpen(true);
            this.update();
            this.checkExitAfterGame();
        }
    }
    
    public int getOwnerPoints(final int slot) {
        return this.points[slot];
    }
    
    public void setPieceType(final int type) {
        this.piecetype = type;
    }
    
    public int getPieceType() {
        return this.piecetype;
    }
    
    public void setGameType() {
        if (this.GameType == 2) {
            this.matchcards.clear();
            for (int i = 0; i < this.getMatchesToWin(); ++i) {
                this.matchcards.add(i);
                this.matchcards.add(i);
            }
        }
    }
    
    public void shuffleList() {
        if (this.GameType == 2) {
            Collections.shuffle(this.matchcards);
        }
        else {
            this.piece = new int[15][15];
        }
    }
    
    public int getCardId(final int slot) {
        return this.matchcards.get(slot - 1);
    }
    
    public int getMatchesToWin() {
        return (this.getPieceType() == 0) ? 6 : ((this.getPieceType() == 1) ? 10 : 15);
    }
    
    public void setLoser(final int type) {
        this.loser = type;
    }
    
    public int getLoser() {
        return this.loser;
    }
    
    public void send(final MapleClient c) {
        if (this.getMCOwner() == null) {
            this.closeShop(false, false);
            return;
        }
        c.getSession().write(PlayerShopPacket.getMiniGame(c, this));
    }
    
    public void setReady(final int slot) {
        this.ready[slot] = !this.ready[slot];
    }
    
    public boolean isReady(final int slot) {
        return this.ready[slot];
    }
    
    public void setPiece(final int move1, final int move2, final int type, final MapleCharacter chr) {
        if (this.piece[move1][move2] == 0 && !this.isOpen()) {
            this.piece[move1][move2] = type;
            this.broadcastToVisitors(PlayerShopPacket.getMiniGameMoveOmok(move1, move2, type));
            boolean found = false;
            for (int y = 0; y < 15; ++y) {
                for (int x = 0; x < 15; ++x) {
                    if (!found && this.searchCombo(x, y, type)) {
                        this.broadcastToVisitors(PlayerShopPacket.getMiniGameResult(this, 2, this.getVisitorSlot(chr)));
                        this.setOpen(true);
                        this.update();
                        this.checkExitAfterGame();
                        found = true;
                    }
                }
            }
            this.nextLoser();
        }
    }
    
    public void nextLoser() {
        ++this.loser;
        if (this.loser > 1) {
            this.loser = 0;
        }
    }
    
    public void exit(final MapleCharacter player) {
        if (player == null) {
            return;
        }
        player.setPlayerShop(null);
        if (this.isOwner(player)) {
            this.update();
            this.removeAllVisitors(3, 1);
        }
        else {
            this.removeVisitor(player);
        }
    }
    
    public boolean isExitAfter(final MapleCharacter player) {
        return this.getVisitorSlot(player) > -1 && this.exitAfter[this.getVisitorSlot(player)];
    }
    
    public void setExitAfter(final MapleCharacter player) {
        if (this.getVisitorSlot(player) > -1) {
            this.exitAfter[this.getVisitorSlot(player)] = !this.exitAfter[this.getVisitorSlot(player)];
        }
    }
    
    public void checkExitAfterGame() {
        for (int i = 0; i < 2; ++i) {
            if (this.exitAfter[i]) {
                this.exitAfter[i] = false;
                this.exit((i == 0) ? this.getMCOwner() : this.chrs[i - 1].get());
            }
        }
    }
    
    public boolean searchCombo(final int x, final int y, final int type) {
        boolean ret = false;
        if (!ret && x < 11) {
            ret = true;
            for (int i = 0; i < 5; ++i) {
                if (this.piece[x + i][y] != type) {
                    ret = false;
                    break;
                }
            }
        }
        if (!ret && y < 11) {
            ret = true;
            for (int i = 0; i < 5; ++i) {
                if (this.piece[x][y + i] != type) {
                    ret = false;
                    break;
                }
            }
        }
        if (!ret && x < 11 && y < 11) {
            ret = true;
            for (int i = 0; i < 5; ++i) {
                if (this.piece[x + i][y + i] != type) {
                    ret = false;
                    break;
                }
            }
        }
        if (!ret && x > 3 && y < 11) {
            ret = true;
            for (int i = 0; i < 5; ++i) {
                if (this.piece[x - i][y + i] != type) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }
    
    public int getScore(final MapleCharacter chr) {
        int score = 2000;
        final int wins = this.getWins(chr);
        final int ties = this.getTies(chr);
        final int losses = this.getLosses(chr);
        if (wins + ties + losses > 0) {
            score += wins * 2;
            score += ties;
            score -= losses * 2;
        }
        return score;
    }
    
    @Override
    public byte getShopType() {
        return (byte)((this.GameType == 1) ? 3 : 4);
    }
    
    public int getWins(final MapleCharacter chr) {
        return Integer.parseInt(this.getData(chr).split(",")[2]);
    }
    
    public int getTies(final MapleCharacter chr) {
        return Integer.parseInt(this.getData(chr).split(",")[1]);
    }
    
    public int getLosses(final MapleCharacter chr) {
        return Integer.parseInt(this.getData(chr).split(",")[0]);
    }
    
    public void setPoints(final int i, final int type) {
        MapleCharacter z;
        if (i == 0) {
            z = this.getMCOwner();
        }
        else {
            z = this.getVisitor(i - 1);
        }
        if (z != null) {
            final String[] data = this.getData(z).split(",");
            data[type] = String.valueOf(Integer.parseInt(data[type]) + 1);
            final StringBuilder newData = new StringBuilder();
            for (int s = 0; s < data.length; ++s) {
                newData.append(data[s]);
                newData.append(",");
            }
            final String newDat = newData.toString();
            z.getQuestNAdd(MapleQuest.getInstance((this.GameType == 1) ? GameConstants.OMOK_SCORE : GameConstants.MATCH_SCORE)).setCustomData(newDat.substring(0, newDat.length() - 1));
        }
    }
    
    public String getData(final MapleCharacter chr) {
        final MapleQuest quest = MapleQuest.getInstance((this.GameType == 1) ? GameConstants.OMOK_SCORE : GameConstants.MATCH_SCORE);
        MapleQuestStatus record;
        if (chr.getQuestNoAdd(quest) == null) {
            record = chr.getQuestNAdd(quest);
            record.setCustomData("0,0,0");
        }
        else {
            record = chr.getQuestNoAdd(quest);
            if (record.getCustomData() == null || record.getCustomData().length() < 5 || record.getCustomData().indexOf(",") == -1) {
                record.setCustomData("0,0,0");
            }
        }
        return record.getCustomData();
    }
    
    public int getRequestedTie() {
        return this.tie;
    }
    
    public void setRequestedTie(final int t) {
        this.tie = t;
    }
    
    public int getRequestedREDO() {
        return this.REDO;
    }
    
    public void setRequestedREDO(final int t) {
        this.REDO = t;
    }
    
    public int getTurn() {
        return this.turn;
    }
    
    public void setTurn(final int t) {
        this.turn = t;
    }
    
    @Override
    public void closeShop(final boolean s, final boolean z) {
        this.removeAllVisitors(3, 1);
        if (this.getMCOwner() != null) {
            this.getMCOwner().setPlayerShop(null);
        }
        this.update();
        this.getMap().removeMapObject(this);
    }
    
    @Override
    public void buy(final MapleClient c, final int z, final short i) {
    }
}
