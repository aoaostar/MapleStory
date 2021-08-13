package server.maps;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import tools.Pair;


public class MapleReactorStats
{
    private byte facingDirection;
    private Point tl;
    private Point br;
    private final Map<Byte, StateData> stateInfo;
    
    public MapleReactorStats() {
        this.stateInfo = new HashMap<Byte, StateData>();
    }
    
    public void setFacingDirection(final byte facingDirection) {
        this.facingDirection = facingDirection;
    }
    
    public byte getFacingDirection() {
        return this.facingDirection;
    }
    
    public void setTL(final Point tl) {
        this.tl = tl;
    }
    
    public void setBR(final Point br) {
        this.br = br;
    }
    
    public Point getTL() {
        return this.tl;
    }
    
    public Point getBR() {
        return this.br;
    }
    
    public void addState(final byte state, final int type, final Pair<Integer, Integer> reactItem, final byte nextState, final int timeOut, final byte canTouch) {
        final StateData newState = new StateData(type, (Pair)reactItem, nextState, timeOut, canTouch);
        this.stateInfo.put(state, newState);
    }
    
    public byte getNextState(final byte state) {
        final StateData nextState = this.stateInfo.get(state);
        if (nextState != null) {
            return nextState.getNextState();
        }
        return -1;
    }
    
    public int getType(final byte state) {
        final StateData nextState = this.stateInfo.get(state);
        if (nextState != null) {
            return nextState.getType();
        }
        return -1;
    }
    
    public Pair<Integer, Integer> getReactItem(final byte state) {
        final StateData nextState = this.stateInfo.get(state);
        if (nextState != null) {
            return nextState.getReactItem();
        }
        return null;
    }
    
    public int getTimeOut(final byte state) {
        final StateData nextState = this.stateInfo.get(state);
        if (nextState != null) {
            return nextState.getTimeOut();
        }
        return -1;
    }
    
    public byte canTouch(final byte state) {
        final StateData nextState = this.stateInfo.get(state);
        if (nextState != null) {
            return nextState.canTouch();
        }
        return 0;
    }
    
    private static class StateData
    {
        private final int type;
        private final int timeOut;
        private final Pair<Integer, Integer> reactItem;
        private final byte nextState;
        private final byte canTouch;
        
        private StateData(final int type, final Pair<Integer, Integer> reactItem, final byte nextState, final int timeOut, final byte canTouch) {
            this.type = type;
            this.reactItem = reactItem;
            this.nextState = nextState;
            this.timeOut = timeOut;
            this.canTouch = canTouch;
        }
        
        private int getType() {
            return this.type;
        }
        
        private byte getNextState() {
            return this.nextState;
        }
        
        private Pair<Integer, Integer> getReactItem() {
            return this.reactItem;
        }
        
        private int getTimeOut() {
            return this.timeOut;
        }
        
        private byte canTouch() {
            return this.canTouch;
        }
    }
}
