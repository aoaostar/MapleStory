package client;

import server.Randomizer;
import tools.data.output.MaplePacketLittleEndianWriter;

public class PlayerRandomStream
{
    private transient long seed1;
    private transient long seed2;
    private transient long seed3;
    private transient long seed1_;
    private transient long seed2_;
    private transient long seed3_;
    private transient long seed1__;
    private transient long seed2__;
    private transient long seed3__;
    private transient long seed1___;
    private transient long seed2___;
    private transient long seed3___;
    
    public PlayerRandomStream() {
        final int v4 = 5;
        this.CRand32__Seed(Randomizer.nextLong(), 1170746341 * v4 - 755606699, 1170746341 * v4 - 755606699);
    }
    
    public void CRand32__Seed(final long s1, final long s2, final long s3) {
        this.seed1 = (s1 | 0x100000L);
        this.seed2 = (s2 | 0x1000L);
        this.seed3 = (s3 | 0x10L);
        this.seed1_ = (s1 | 0x100000L);
        this.seed2_ = (s2 | 0x1000L);
        this.seed3_ = (s3 | 0x10L);
        this.seed1__ = (s1 | 0x100000L);
        this.seed2__ = (s2 | 0x1000L);
        this.seed3__ = (s3 | 0x10L);
    }
    
    public long CRand32__Random() {
        final long v4 = this.seed1;
        final long v5 = this.seed2;
        final long v6 = this.seed3;
        final long v7 = this.seed1;
        final long v8 = (v4 & 0xFFFFFFFFFFFFFFFEL) << 12 ^ ((v7 & 0x7FFC0L) ^ v4 >> 13) >> 6;
        final long v9 = 16L * (v5 & 0xFFFFFFFFFFFFFFF8L) ^ (v5 >> 2 ^ (v5 & 0x3F800000L)) >> 23;
        final long v10 = (v6 & 0xFFFFFFFFFFFFFFF0L) << 17 ^ (v6 >> 3 ^ (v6 & 0x1FFFFF00L)) >> 8;
        this.seed3_ = (v10 & 0xFFFFFFFFL);
        this.seed1_ = (v8 & 0xFFFFFFFFL);
        this.seed2_ = (v9 & 0xFFFFFFFFL);
        return (v8 ^ v9 ^ v10) & 0xFFFFFFFFL;
    }
    
    public long CRand32__Random_Character() {
        final long v4 = this.seed1_;
        final long v5 = this.seed2_;
        final long v6 = this.seed3_;
        final long v7 = this.seed1_;
        final long v8 = (v4 & 0xFFFFFFFFFFFFFFFEL) << 12 ^ ((v7 & 0x7FFC0L) ^ v4 >> 13) >> 6;
        final long v9 = 16L * (v5 & 0xFFFFFFFFFFFFFFF8L) ^ (v5 >> 2 ^ (v5 & 0x3F800000L)) >> 23;
        final long v10 = (v6 & 0xFFFFFFFFFFFFFFF0L) << 17 ^ (v6 >> 3 ^ (v6 & 0x1FFFFF00L)) >> 8;
        this.seed3_ = (v10 & 0xFFFFFFFFL);
        this.seed1_ = (v8 & 0xFFFFFFFFL);
        this.seed2_ = (v9 & 0xFFFFFFFFL);
        return (v8 ^ v9 ^ v10) & 0xFFFFFFFFL;
    }
    
    public long CRand32__Random_CheckDamageMiss() {
        final long v4 = this.seed1__;
        final long v5 = this.seed2__;
        final long v6 = this.seed3__;
        final long v7 = this.seed1__;
        final long v8 = (v4 & 0xFFFFFFFFFFFFFFFEL) << 12 ^ ((v7 & 0x7FFC0L) ^ v4 >> 13) >> 6;
        final long v9 = 16L * (v5 & 0xFFFFFFFFFFFFFFF8L) ^ (v5 >> 2 ^ (v5 & 0x3F800000L)) >> 23;
        final long v10 = (v6 & 0xFFFFFFFFFFFFFFF0L) << 17 ^ (v6 >> 3 ^ (v6 & 0x1FFFFF00L)) >> 8;
        this.seed3_ = (v10 & 0xFFFFFFFFL);
        this.seed1_ = (v8 & 0xFFFFFFFFL);
        this.seed2_ = (v9 & 0xFFFFFFFFL);
        return (v8 ^ v9 ^ v10) & 0xFFFFFFFFL;
    }
    
    public long CRand32__Random_ForMonster() {
        final long v4 = this.seed1___;
        final long v5 = this.seed2___;
        final long v6 = this.seed3___;
        final long v7 = this.seed1___;
        final long v8 = (v4 & 0xFFFFFFFFFFFFFFFEL) << 12 ^ ((v7 & 0x7FFC0L) ^ v4 >> 13) >> 6;
        final long v9 = 16L * (v5 & 0xFFFFFFFFFFFFFFF8L) ^ (v5 >> 2 ^ (v5 & 0x3F800000L)) >> 23;
        final long v10 = (v6 & 0xFFFFFFFFFFFFFFF0L) << 17 ^ (v6 >> 3 ^ (v6 & 0x1FFFFF00L)) >> 8;
        this.seed3_ = (v10 & 0xFFFFFFFFL);
        this.seed1_ = (v8 & 0xFFFFFFFFL);
        this.seed2_ = (v9 & 0xFFFFFFFFL);
        return (v8 ^ v9 ^ v10) & 0xFFFFFFFFL;
    }
    
    public void connectData(final MaplePacketLittleEndianWriter mplew) {
        final long v5 = this.CRand32__Random();
        final long s2 = this.CRand32__Random();
        final long v6 = this.CRand32__Random();
        this.CRand32__Seed(v5, s2, v6);
        mplew.writeInt((int)v5);
        mplew.writeInt((int)s2);
        mplew.writeInt((int)v6);
    }
}
