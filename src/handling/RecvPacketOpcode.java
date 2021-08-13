package handling;

import java.util.*;
import java.io.*;
import constants.*;

public enum RecvPacketOpcode implements WritableIntValueHolder
{
    PONG(false), 
    LOGIN_PASSWORD(false), 
    SERVERLIST_REQUEST, 
    LICENSE_REQUEST, 
    CHARLIST_REQUEST, 
    SET_GENDER, 
    SERVERSTATUS_REQUEST, 
    CHAR_SELECT, 
    CHECK_CHAR_NAME, 
    CREATE_CHAR, 
    DELETE_CHAR, 
    ERROR_LOG, 
    PACKET_ERROR, 
    RSA_KEY(false), 
    PLAYER_LOGGEDIN(false), 
    STRANGE_DATA, 
    CHANGE_MAP, 
    CHANGE_CHANNEL, 
    ENTER_CASH_SHOP, 
    MOVE_PLAYER, 
    CANCEL_CHAIR, 
    USE_CHAIR, 
    CLOSE_RANGE_ATTACK, 
    RANGED_ATTACK, 
    MAGIC_ATTACK, 
    PASSIVE_ENERGY, 
    TAKE_DAMAGE, 
    GENERAL_CHAT, 
    CLOSE_CHALKBOARD, 
    FACE_EXPRESSION, 
    USE_ITEMEFFECT, 
    REVIVE_ITEM, 
    MONSTER_BOOK_COVER, 
    NPC_TALK, 
    NPC_TALK_MORE, 
    NPC_SHOP, 
    STORAGE, 
    USE_HIRED_MERCHANT, 
    MERCH_ITEM_STORE, 
    DUEY_ACTION, 
    ITEM_SORT, 
    ITEM_GATHER, 
    ITEM_MOVE, 
    USE_ITEM, 
    CANCEL_ITEM_EFFECT, 
    USE_FISHING_ITEM, 
    USE_SUMMON_BAG, 
    USE_MOUNT_FOOD, 
    SCRIPTED_ITEM, 
    USE_CASH_ITEM, 
    USE_CATCH_ITEM, 
    USE_SKILL_BOOK, 
    USE_RETURN_SCROLL, 
    USE_UPGRADE_SCROLL, 
    DISTRIBUTE_AP, 
    AUTO_ASSIGN_AP, 
    HEAL_OVER_TIME, 
    DISTRIBUTE_SP, 
    SPECIAL_MOVE, 
    CANCEL_BUFF, 
    SKILL_EFFECT, 
    MESO_DROP, 
    GIVE_FAME, 
    CHAR_INFO_REQUEST, 
    CANCEL_DEBUFF, 
    CHANGE_MAP_SPECIAL, 
    USE_INNER_PORTAL, 
    TROCK_ADD_MAP, 
    LIE_DETECTOR, 
    LIE_DETECTOR_RESPONSE, 
    LIE_DETECTOR_REFRESH, 
    QUEST_ACTION, 
    EFFECT_ON_OFF, 
    QUEST_KJ, 
    SKILL_MACRO, 
    REPORT_PLAYER, 
    ITEM_BAOWU, 
    ITEM_MZD, 
    ITEM_MAKER, 
    PARTYCHAT, 
    WHISPER, 
    SPOUSE_CHAT, 
    MESSENGER, 
    PLAYER_INTERACTION, 
    PARTY_OPERATION, 
    DENY_PARTY_REQUEST, 
    GUILD_OPERATION, 
    DENY_GUILD_REQUEST, 
    BUDDYLIST_MODIFY, 
    NOTE_ACTION, 
    USE_DOOR, 
    CHANGE_KEYMAP, 
    RPS_GAME, 
    RING_ACTION, 
    ALLIANCE_OPERATION, 
    DENY_ALLIANCE_REQUEST, 
    BBS_OPERATION, 
    ENTER_MTS, 
    ITEM_SUNZI, 
    TRANSFORM_PLAYER, 
    REQUEST_FAMILY, 
    OPEN_FAMILY, 
    FAMILY_OPERATION, 
    DELETE_JUNIOR, 
    DELETE_SENIOR, 
    ACCEPT_FAMILY, 
    USE_FAMILY, 
    FAMILY_PRECEPT, 
    FAMILY_SUMMON, 
    CYGNUS_SUMMON, 
    ARAN_COMBO, 
    MOVE_SUMMON, 
    SUMMON_ATTACK, 
    DAMAGE_SUMMON, 
    MOVE_LIFE, 
    AUTO_AGGRO, 
    FRIENDLY_DAMAGE, 
    MONSTER_BOMB, 
    NPC_ACTION, 
    ITEM_PICKUP, 
    DAMAGE_REACTOR, 
    TOUCH_REACTOR, 
    SNOWBALL, 
    LEFT_KNOCK_BACK, 
    COCONUT, 
    NEW_SX, 
    MONSTER_CARNIVAL, 
    SHIP_OBJECT, 
    PARTY_SS, 
    PLAYER_UPDATE, 
    MAPLETV, 
    MTS_OP, 
    BEANS_GAME1, 
    BEANS_GAME2, 
    TOUCHING_CS, 
    BUY_CS_ITEM, 
    COUPON_CODE, 
    SPAWN_PET, 
    MOVE_PET, 
    PET_AUTO_POT, 
    PET_CHAT, 
    PET_COMMAND, 
    PET_LOOT, 
    PET_FOOD, 
    ChatRoom_SYSTEM, 
    MOONRABBIT_HP, 
    PET_EXCEPTIONLIST, 
    SERVERLIST_REREQUEST, 
    AFTER_LOGIN, 
    REGISTER_PIN, 
    PLAYER_DC, 
    VIEW_ALL_CHAR, 
    PICK_ALL_CHAR, 
    CS_UPDATE, 
    TOUCHING_MTS, 
    MTS_TAB, 
    OWL, 
    OWL_WARP, 
    USE_OWL_MINERVA, 
    UPDATE_QUEST, 
    USE_ITEM_QUEST;
    
    private short code;
    private boolean CheckState;
    
    @Override
    public void setValue(final short code) {
        this.code = code;
    }
    
    @Override
    public final short getValue() {
        return this.code;
    }
    
    private RecvPacketOpcode() {
        this.code = -2;
        this.CheckState = true;
    }
    
    private RecvPacketOpcode(final boolean CheckState) {
        this.code = -2;
        this.CheckState = CheckState;
    }
    
    public final boolean NeedsChecking() {
        return this.CheckState;
    }
    
    public static Properties getDefaultProperties() throws FileNotFoundException, IOException {
        final Properties props = new Properties();
        final FileInputStream fileInputStream = new FileInputStream("recvops.properties");
        props.load(fileInputStream);
        fileInputStream.close();
        return props;
    }
    
    public static void reloadValues() {
        try {
            if (ServerConstants.loadop) {
                final Properties props = new Properties();
                props.load(RecvPacketOpcode.class.getClassLoader().getResourceAsStream("recvops.properties"));
                ExternalCodeTableGetter.populateValues(props, values());
            } else {
                ExternalCodeTableGetter.populateValues(getDefaultProperties(), values());
            }
        }
        catch (IOException e) {
            throw new RuntimeException("加载 recvops.properties 文件出现错误", e);
        }
    }
    
    public static boolean isSpamHeader(final RecvPacketOpcode header) {
        final String name = header.name();
        switch (name) {
            case "PONG":
            case "NPC_ACTION":
            case "MOVE_LIFE":
            case "MOVE_PLAYER":
            case "MOVE_ANDROID":
            case "MOVE_SUMMON":
            case "AUTO_AGGRO":
            case "HEAL_OVER_TIME":
            case "BUTTON_PRESSED":
            case "STRANGE_DATA":
            case "TAKE_DAMAGE": {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        reloadValues();
    }
}
