package handling;

import java.util.*;
import java.io.*;
import constants.*;

public enum SendPacketOpcode implements WritableIntValueHolder
{
    PING, 
    LOGIN_STATUS, 
    LICENSE_RESULT, 
    SERVERSTATUS, 
    CHOOSE_GENDER, 
    GENDER_SET, 
    SERVERLIST, 
    CHARLIST, 
    SERVER_IP, 
    CHAR_NAME_RESPONSE, 
    ADD_NEW_CHAR_ENTRY, 
    CHANNEL_SELECTED, 
    CHANGE_CHANNEL, 
    MODIFY_INVENTORY_ITEM, 
    UPDATE_INVENTORY_SLOT, 
    UPDATE_STATS, 
    GIVE_BUFF, 
    CANCEL_BUFF, 
    TEMP_STATS, 
    TEMP_STATS_RESET, 
    UPDATE_SKILLS, 
    SKILL_USE_RESULT, 
    FAME_RESPONSE, 
    SHOW_STATUS_INFO, 
    SHOW_NOTES, 
    TROCK_LOCATIONS, 
    LIE_DETECTOR, 
    CLAIM_RESULT, 
    CLAIM_STATUS_CHANGED, 
    SET_TAMING_MOB_INFO, 
    UPDATE_MOUNT, 
    SHOW_QUEST_COMPLETION, 
    SEND_TITLE_BOX, 
    USE_SKILL_BOOK, 
    FINISH_SORT, 
    FINISH_GATHER, 
    CHAR_INFO, 
    PARTY_OPERATION, 
    BUDDYLIST, 
    GUILD_OPERATION, 
    ALLIANCE_OPERATION, 
    SPAWN_PORTAL, 
    SERVERMESSAGE, 
    OWL_OF_MINERVA, 
    SHOW_PREDICT_CARD, 
    ENGAGE_REQUEST, 
    ENGAGE_RESULT, 
    YELLOW_CHAT, 
    AVATAR_MEGA, 
    PLAYER_NPC, 
    MONSTERBOOK_ADD, 
    MONSTERBOOK_CHANGE_COVER, 
    ENERGY, 
    GHOST_POINT, 
    GHOST_STATUS, 
    FAIRY_PEND_MSG, 
    SEND_PEDIGREE, 
    OPEN_FAMILY, 
    FAMILY_MESSAGE, 
    FAMILY_INVITE, 
    FAMILY_JUNIOR, 
    SENIOR_MESSAGE, 
    FAMILY, 
    REP_INCREASE, 
    FAMILY_LOGGEDIN, 
    FAMILY_BUFF, 
    FAMILY_USE_REQUEST, 
    LEVEL_UPDATE, 
    MARRIAGE_UPDATE, 
    JOB_UPDATE, 
    SET_BUY_EQUIP_EXT, 
    TOP_MSG, 
    DATA_CRC_CHECK_FAILED, 
    BBS_OPERATION, 
    FISHING_BOARD_UPDATE, 
    OPEN_WEB, 
    CHAR_CASH, 
    SKILL_MACRO, 
    WARP_TO_MAP, 
    MTS_OPEN, 
    CS_OPEN, 
    BLOCK_MSG, 
    SHOW_EQUIP_EFFECT, 
    MULTICHAT, 
    WHISPER, 
    SPOUSE_CHAT, 
    BOSS_ENV, 
    FORCED_MAP_EQUIP, 
    MAP_EFFECT, 
    CASH_SONG, 
    GM_EFFECT, 
    OX_QUIZ, 
    GMEVENT_INSTRUCTIONS, 
    CLOCK, 
    BOAT_EFFECT, 
    BOAT_PACKET, 
    STOP_CLOCK, 
    ARIANT_SCOREBOARD, 
    PYRAMID_UPDATE, 
    PYRAMID_RESULT, 
    MOVE_PLATFORM, 
    SPAWN_PLAYER, 
    REMOVE_PLAYER_FROM_MAP, 
    CHATTEXT, 
    CHALKBOARD, 
    UPDATE_CHAR_BOX, 
    SHOW_SCROLL_EFFECT, 
    FISHING_CAUGHT, 
    SPAWN_PET, 
    MOVE_PET, 
    PET_CHAT, 
    PET_NAMECHANGE, 
    PET_COMMAND, 
    PET_FLAG_CHANGE, 
    SPAWN_SUMMON, 
    REMOVE_SUMMON, 
    MOVE_SUMMON, 
    SUMMON_ATTACK, 
    DAMAGE_SUMMON, 
    SUMMON_SKILL, 
    MOVE_PLAYER, 
    CLOSE_RANGE_ATTACK, 
    RANGED_ATTACK, 
    MAGIC_ATTACK, 
    ENERGY_ATTACK, 
    SKILL_EFFECT, 
    CANCEL_SKILL_EFFECT, 
    DAMAGE_PLAYER, 
    FACIAL_EXPRESSION, 
    SHOW_ITEM_EFFECT, 
    SHOW_CHAIR, 
    UPDATE_CHAR_LOOK, 
    SHOW_FOREIGN_EFFECT, 
    GIVE_FOREIGN_BUFF, 
    CANCEL_FOREIGN_BUFF, 
    UPDATE_PARTYMEMBER_HP, 
    Animation_EFFECT, 
    CANCEL_CHAIR, 
    SHOW_ITEM_GAIN_INCHAT, 
    DOJO_WARP_UP, 
    MESOBAG_SUCCESS, 
    MESOBAG_FAILURE, 
    UPDATE_QUEST_INFO, 
    PLAYER_HINT, 
    EVENT_WINDOW, 
    ARAN_COMBO, 
    COOLDOWN, 
    SPAWN_MONSTER, 
    KILL_MONSTER, 
    SPAWN_MONSTER_CONTROL, 
    MOVE_MONSTER, 
    MOVE_MONSTER_RESPONSE, 
    APPLY_MONSTER_STATUS, 
    CANCEL_MONSTER_STATUS, 
    MOB_TO_MOB_DAMAGE, 
    DAMAGE_MONSTER, 
    ARIANT_THING, 
    SHOW_MONSTER_HP, 
    SHOW_MAGNET, 
    CATCH_ARIANT, 
    CATCH_MONSTER, 
    SPAWN_NPC, 
    REMOVE_NPC, 
    SPAWN_NPC_REQUEST_CONTROLLER, 
    NPC_ACTION, 
    SPAWN_HIRED_MERCHANT, 
    DESTROY_HIRED_MERCHANT, 
    UPDATE_HIRED_MERCHANT, 
    DROP_ITEM_FROM_MAPOBJECT, 
    REMOVE_ITEM_FROM_MAP, 
    SPAWN_LOVE, 
    REMOVE_LOVE, 
    SPAWN_MIST, 
    REMOVE_MIST, 
    SPAWN_DOOR, 
    REMOVE_DOOR, 
    REACTOR_HIT, 
    REACTOR_SPAWN, 
    REACTOR_DESTROY, 
    ROLL_SNOWBALL, 
    HIT_SNOWBALL, 
    SNOWBALL_MESSAGE, 
    LEFT_KNOCK_BACK, 
    HIT_COCONUT, 
    COCONUT_SCORE, 
    MONSTER_CARNIVAL_START, 
    MONSTER_CARNIVAL_OBTAINED_CP, 
    MONSTER_CARNIVAL_PARTY_CP, 
    MONSTER_CARNIVAL_SUMMON, 
    MONSTER_CARNIVAL_DIED, 
    ENGLISH_QUIZ, 
    ZAKUM_SHRINE, 
    NPC_TALK, 
    OPEN_NPC_SHOP, 
    CONFIRM_SHOP_TRANSACTION, 
    OPEN_STORAGE, 
    MERCH_ITEM_MSG, 
    MERCH_ITEM_STORE, 
    MESSENGER, 
    PLAYER_INTERACTION, 
    BEANS_GAME_MESSAGE, 
    BEANS_GAME1, 
    BEANS_GAME2, 
    UPDATE_BEANS, 
    DUEY, 
    CS_UPDATE, 
    CS_OPERATION, 
    KEYMAP, 
    AUTO_HP_POT, 
    AUTO_MP_POT, 
    SEND_TV, 
    REMOVE_TV, 
    ENABLE_TV, 
    MTS_OPERATION, 
    MTS_OPERATION2, 
    VICIOUS_HAMMER, 
    CYGNUS_INTRO_LOCK, 
    CYGNUS_INTRO_DISABLE_UI, 
    TUTORIAL_SUMMON, 
    SUMMON_HINT, 
    SUMMON_HINT_MSG, 
    ARIANT_SCORE_UPDATE, 
    SEND_LINK, 
    PIN_OPERATION, 
    PIN_ASSIGNED, 
    ALL_CHARLIST, 
    CYGNUS_CHAR_CREATED, 
    CURRENT_MAP_WARP, 
    SERVER_BLOCKED, 
    HORNTAIL_SHRINE, 
    RPS_GAME, 
    UPDATE_ENV, 
    GET_MTS_TOKENS, 
    TALK_MONSTER, 
    CHAOS_ZAKUM_SHRINE, 
    CHAOS_HORNTAIL_SHRINE, 
    REMOVE_TALK_MONSTER;
    
    private short code;
    
    private SendPacketOpcode() {
        this.code = -2;
    }
    
    @Override
    public void setValue(final short code) {
        this.code = code;
    }
    
    @Override
    public short getValue() {
        return this.code;
    }
    
    public static Properties getDefaultProperties() throws FileNotFoundException, IOException {
        final Properties props = new Properties();
        final FileInputStream fileInputStream = new FileInputStream("sendops.properties");
        props.load(fileInputStream);
        fileInputStream.close();
        return props;
    }
    
    public static void reloadValues() {
        try {
            if (ServerConstants.loadop) {
                final Properties props = new Properties();
                props.load(SendPacketOpcode.class.getClassLoader().getResourceAsStream("sendops.properties"));
                ExternalCodeTableGetter.populateValues(props, values());
            }
            else {
                ExternalCodeTableGetter.populateValues(getDefaultProperties(), values());
            }
        }
        catch (IOException e) {
            throw new RuntimeException("加载 sendops.properties 文件出现错误", e);
        }
    }
    
    public static boolean isSpamHeader(final SendPacketOpcode opcode) {
        final String name = opcode.name();
        switch (name) {
            case "WARP_TO_MAP":
            case "PING":
            case "NPC_ACTION":
            case "UPDATE_STATS":
            case "MOVE_PLAYER":
            case "SPAWN_NPC":
            case "SPAWN_NPC_REQUEST_CONTROLLER":
            case "REMOVE_NPC":
            case "MOVE_MONSTER":
            case "MOVE_MONSTER_RESPONSE":
            case "SPAWN_MONSTER":
            case "SPAWN_MONSTER_CONTROL":
            case "ANDROID_MOVE": {
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
