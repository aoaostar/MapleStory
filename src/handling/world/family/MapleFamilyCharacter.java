package handling.world.family;

import client.MapleCharacter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class MapleFamilyCharacter implements Serializable
{
    public static long serialVersionUID;
    private int level;
    private int id;
    private int channel;
    private int jobid;
    private int familyid;
    private int seniorid;
    private int currentrep;
    private int totalrep;
    private int junior1;
    private int junior2;
    private boolean online;
    private String name;
    private List<Integer> pedigree;
    private int descendants;
    
    public MapleFamilyCharacter(final MapleCharacter c, final int fid, final int sid, final int j1, final int j2) {
        this.channel = -1;
        this.pedigree = new ArrayList<Integer>();
        this.descendants = 0;
        this.name = c.getName();
        this.level = c.getLevel();
        this.id = c.getId();
        this.channel = c.getClient().getChannel();
        this.jobid = c.getJob();
        this.familyid = fid;
        this.junior1 = j1;
        this.junior2 = j2;
        this.seniorid = sid;
        this.currentrep = c.getCurrentRep();
        this.totalrep = c.getTotalRep();
        this.online = true;
    }
    
    public MapleFamilyCharacter(final int _id, final int _lv, final String _name, final int _channel, final int _job, final int _fid, final int _sid, final int _jr1, final int _jr2, final int _crep, final int _trep, final boolean _on) {
        this.channel = -1;
        this.pedigree = new ArrayList<Integer>();
        this.descendants = 0;
        this.level = _lv;
        this.id = _id;
        this.name = _name;
        if (_on) {
            this.channel = _channel;
        }
        this.jobid = _job;
        this.online = _on;
        this.familyid = _fid;
        this.seniorid = _sid;
        this.currentrep = _crep;
        this.totalrep = _trep;
        this.junior1 = _jr1;
        this.junior2 = _jr2;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int l) {
        this.level = l;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setChannel(final int ch) {
        this.channel = ch;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public int getJobId() {
        return this.jobid;
    }
    
    public void setJobId(final int job) {
        this.jobid = job;
    }
    
    public int getCurrentRep() {
        return this.currentrep;
    }
    
    public void setCurrentRep(final int cr) {
        this.currentrep = cr;
    }
    
    public int getTotalRep() {
        return this.totalrep;
    }
    
    public void setTotalRep(final int tr) {
        this.totalrep = tr;
    }
    
    public int getJunior1() {
        return this.junior1;
    }
    
    public int getJunior2() {
        return this.junior2;
    }
    
    public void setJunior1(final int trs) {
        this.junior1 = trs;
    }
    
    public void setJunior2(final int trs) {
        this.junior2 = trs;
    }
    
    public int getSeniorId() {
        return this.seniorid;
    }
    
    public void setSeniorId(final int si) {
        this.seniorid = si;
    }
    
    public int getFamilyId() {
        return this.familyid;
    }
    
    public void setFamilyId(final int fi) {
        this.familyid = fi;
    }
    
    public boolean isOnline() {
        return this.online;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof MapleFamilyCharacter)) {
            return false;
        }
        final MapleFamilyCharacter o = (MapleFamilyCharacter)other;
        return o.getId() == this.id && o.getName().equals(this.name);
    }
    
    public void setOnline(final boolean f) {
        this.online = f;
    }
    
    public List<MapleFamilyCharacter> getAllJuniors(final MapleFamily fam) {
        final List<MapleFamilyCharacter> ret = new ArrayList<MapleFamilyCharacter>();
        ret.add(this);
        if (this.junior1 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior1);
            if (chr != null) {
                ret.addAll(chr.getAllJuniors(fam));
            }
        }
        if (this.junior2 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior2);
            if (chr != null) {
                ret.addAll(chr.getAllJuniors(fam));
            }
        }
        return ret;
    }
    
    public List<MapleFamilyCharacter> getOnlineJuniors(final MapleFamily fam) {
        final List<MapleFamilyCharacter> ret = new ArrayList<MapleFamilyCharacter>();
        ret.add(this);
        if (this.junior1 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior1);
            if (chr != null) {
                if (chr.isOnline()) {
                    ret.add(chr);
                }
                if (chr.getJunior1() > 0) {
                    final MapleFamilyCharacter chr2 = fam.getMFC(chr.getJunior1());
                    if (chr2 != null && chr2.isOnline()) {
                        ret.add(chr2);
                    }
                }
                if (chr.getJunior2() > 0) {
                    final MapleFamilyCharacter chr2 = fam.getMFC(chr.getJunior2());
                    if (chr2 != null && chr2.isOnline()) {
                        ret.add(chr2);
                    }
                }
            }
        }
        if (this.junior2 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior2);
            if (chr != null) {
                if (chr.isOnline()) {
                    ret.add(chr);
                }
                if (chr.getJunior1() > 0) {
                    final MapleFamilyCharacter chr2 = fam.getMFC(chr.getJunior1());
                    if (chr2 != null && chr2.isOnline()) {
                        ret.add(chr2);
                    }
                }
                if (chr.getJunior2() > 0) {
                    final MapleFamilyCharacter chr2 = fam.getMFC(chr.getJunior2());
                    if (chr2 != null && chr2.isOnline()) {
                        ret.add(chr2);
                    }
                }
            }
        }
        return ret;
    }
    
    public List<Integer> getPedigree() {
        return this.pedigree;
    }
    
    public void resetPedigree(final MapleFamily fam) {
        (this.pedigree = new ArrayList<Integer>()).add(this.id);
        if (this.seniorid > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.seniorid);
            if (chr != null) {
                this.pedigree.add(this.seniorid);
                if (chr.getSeniorId() > 0) {
                    this.pedigree.add(chr.getSeniorId());
                }
                if (chr.getJunior1() > 0 && chr.getJunior1() != this.id) {
                    this.pedigree.add(chr.getJunior1());
                }
                else if (chr.getJunior2() > 0 && chr.getJunior2() != this.id) {
                    this.pedigree.add(chr.getJunior2());
                }
            }
        }
        if (this.junior1 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior1);
            if (chr != null) {
                this.pedigree.add(this.junior1);
                if (chr.getJunior1() > 0) {
                    this.pedigree.add(chr.getJunior1());
                }
                if (chr.getJunior2() > 0) {
                    this.pedigree.add(chr.getJunior2());
                }
            }
        }
        if (this.junior2 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior2);
            if (chr != null) {
                this.pedigree.add(this.junior2);
                if (chr.getJunior1() > 0) {
                    this.pedigree.add(chr.getJunior1());
                }
                if (chr.getJunior2() > 0) {
                    this.pedigree.add(chr.getJunior2());
                }
            }
        }
    }
    
    public int getDescendants() {
        return this.descendants;
    }
    
    public int resetDescendants(final MapleFamily fam) {
        this.descendants = 0;
        if (this.junior1 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior1);
            if (chr != null) {
                this.descendants += 1 + chr.resetDescendants(fam);
            }
        }
        if (this.junior2 > 0) {
            final MapleFamilyCharacter chr = fam.getMFC(this.junior2);
            if (chr != null) {
                this.descendants += 1 + chr.resetDescendants(fam);
            }
        }
        return this.descendants;
    }

    public int resetGenerations(MapleFamily fam) {
        int descendants1 = 0, descendants2 = 0;
        if (this.junior1 > 0) {
            MapleFamilyCharacter chr = fam.getMFC(this.junior1);
            if (chr != null)
                descendants1 = chr.resetGenerations(fam);
        }
        if (this.junior2 > 0) {
            MapleFamilyCharacter chr = fam.getMFC(this.junior2);
            if (chr != null)
                descendants2 = chr.resetGenerations(fam);
        }
        int ret = Math.max(descendants1, descendants2);
        return ret + ((ret > 0) ? 1 : 0);
    }
    
    public int getNoJuniors() {
        int ret = 0;
        if (this.junior1 > 0) {
            ++ret;
        }
        if (this.junior2 > 0) {
            ++ret;
        }
        return ret;
    }
    
    static {
        MapleFamilyCharacter.serialVersionUID = 2058609046116597760L;
    }
}
