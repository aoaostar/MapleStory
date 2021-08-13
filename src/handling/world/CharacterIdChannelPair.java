package handling.world;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CharacterIdChannelPair implements Externalizable, Comparable<CharacterIdChannelPair>
{
    private int charid;
    private int channel;
    
    public CharacterIdChannelPair() {
        this.charid = 0;
        this.channel = 1;
    }
    
    public CharacterIdChannelPair(final int charid, final int channel) {
        this.charid = 0;
        this.channel = 1;
        this.charid = charid;
        this.channel = channel;
    }
    
    public int getCharacterId() {
        return this.charid;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.charid = in.readInt();
        this.channel = in.readByte();
    }
    
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeInt(this.charid);
        out.writeByte(this.channel);
    }
    
    @Override
    public int compareTo(final CharacterIdChannelPair o) {
        return this.channel - o.channel;
    }
}
