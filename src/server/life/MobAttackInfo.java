package server.life;

public class MobAttackInfo
{
    private boolean isDeadlyAttack;
    private int mpBurn;
    private int mpCon;
    private int diseaseSkill;
    private int diseaseLevel;
    
    public void setDeadlyAttack(final boolean isDeadlyAttack) {
        this.isDeadlyAttack = isDeadlyAttack;
    }
    
    public boolean isDeadlyAttack() {
        return this.isDeadlyAttack;
    }
    
    public void setMpBurn(final int mpBurn) {
        this.mpBurn = mpBurn;
    }
    
    public int getMpBurn() {
        return this.mpBurn;
    }
    
    public void setDiseaseSkill(final int diseaseSkill) {
        this.diseaseSkill = diseaseSkill;
    }
    
    public int getDiseaseSkill() {
        return this.diseaseSkill;
    }
    
    public void setDiseaseLevel(final int diseaseLevel) {
        this.diseaseLevel = diseaseLevel;
    }
    
    public int getDiseaseLevel() {
        return this.diseaseLevel;
    }
    
    public void setMpCon(final int mpCon) {
        this.mpCon = mpCon;
    }
    
    public int getMpCon() {
        return this.mpCon;
    }
}
