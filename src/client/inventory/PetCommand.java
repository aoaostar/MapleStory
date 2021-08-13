package client.inventory;

public class PetCommand
{
    private final int petId;
    private final int skillId;
    private final int prob;
    private final int inc;
    
    public PetCommand(final int petId, final int skillId, final int prob, final int inc) {
        this.petId = petId;
        this.skillId = skillId;
        this.prob = prob;
        this.inc = inc;
    }
    
    public int getPetId() {
        return this.petId;
    }
    
    public int getSkillId() {
        return this.skillId;
    }
    
    public int getProbability() {
        return this.prob;
    }
    
    public int getIncrease() {
        return this.inc;
    }
}
