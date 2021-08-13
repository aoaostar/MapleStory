package server.life;

public enum ElementalEffectiveness
{
    正常, 
    免疫, 
    增强, 
    虚弱, 
    NEUTRAL;
    
    public static ElementalEffectiveness getByNumber(final int num) {
        switch (num) {
            case 1: {
                return ElementalEffectiveness.免疫;
            }
            case 2: {
                return ElementalEffectiveness.增强;
            }
            case 3: {
                return ElementalEffectiveness.虚弱;
            }
            case 4: {
                return ElementalEffectiveness.NEUTRAL;
            }
            default: {
                throw new IllegalArgumentException("Unkown effectiveness: " + num);
            }
        }
    }
}
