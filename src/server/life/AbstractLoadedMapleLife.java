package server.life;

import server.maps.AbstractAnimatedMapleMapObject;

public abstract class AbstractLoadedMapleLife extends AbstractAnimatedMapleMapObject
{
    private int id;
    private int f;
    private boolean hide;
    private int fh;
    private int originFh;
    private int cy;
    private int rx0;
    private int rx1;
    private String ctype;
    private int mtime;
    
    public AbstractLoadedMapleLife(final int id) {
        this.id = id;
    }
    
    public AbstractLoadedMapleLife(final AbstractLoadedMapleLife life) {
        this(life.getId());
        this.f = life.f;
        this.hide = life.hide;
        this.fh = life.fh;
        this.originFh = life.fh;
        this.cy = life.cy;
        this.rx0 = life.rx0;
        this.rx1 = life.rx1;
        this.ctype = life.ctype;
        this.mtime = life.mtime;
    }
    
    public int getF() {
        return this.f;
    }
    
    public void setF(final int f) {
        this.f = f;
    }
    
    public boolean isHidden() {
        return this.hide;
    }
    
    public void setHide(final boolean hide) {
        this.hide = hide;
    }
    
    public int originFh() {
        return this.originFh;
    }
    
    public int getFh() {
        return this.fh;
    }
    
    public void setFh(final int fh) {
        this.fh = fh;
    }
    
    public int getCy() {
        return this.cy;
    }
    
    public void setCy(final int cy) {
        this.cy = cy;
    }
    
    public int getRx0() {
        return this.rx0;
    }
    
    public void setRx0(final int rx0) {
        this.rx0 = rx0;
    }
    
    public int getRx1() {
        return this.rx1;
    }
    
    public void setRx1(final int rx1) {
        this.rx1 = rx1;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getMTime() {
        return this.mtime;
    }
    
    public void setMTime(final int mtime) {
        this.mtime = mtime;
    }
    
    public String getCType() {
        return this.ctype;
    }
    
    public void setCType(final String type) {
        this.ctype = type;
    }
}
