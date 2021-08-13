package server.life;

public enum Element
{
    NEUTRAL, 
    PHYSICAL, 
    FIRE, 
    ICE, 
    LIGHTING, 
    POISON, 
    HOLY, 
    DARKNESS;
    
    public static Element getFromChar(final char c) {
        switch (Character.toUpperCase(c)) {
            case 'F': {
                return Element.FIRE;
            }
            case 'I': {
                return Element.ICE;
            }
            case 'L': {
                return Element.LIGHTING;
            }
            case 'S': {
                return Element.POISON;
            }
            case 'H': {
                return Element.HOLY;
            }
            case 'P': {
                return Element.NEUTRAL;
            }
            default: {
                throw new IllegalArgumentException("unknown elemnt char " + c);
            }
        }
    }
}
