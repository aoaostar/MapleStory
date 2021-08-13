package server;

import constants.GameConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomRewards
{
    private static final RandomRewards instance;
    private List<Integer> compiledGold;
    private List<Integer> compiledSilver;
    private List<Integer> compiledFishing;
    private List<Integer> compiledEvent;
    private List<Integer> compiledEventC;
    private List<Integer> compiledEventB;
    private List<Integer> compiledEventA;
    
    public static RandomRewards getInstance() {
        return RandomRewards.instance;
    }
    
    protected RandomRewards() {
        this.compiledGold = null;
        this.compiledSilver = null;
        this.compiledFishing = null;
        this.compiledEvent = null;
        this.compiledEventC = null;
        this.compiledEventB = null;
        this.compiledEventA = null;
        System.out.println("加载 随机奖励 :::");
        List<Integer> returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.goldrewards);
        this.compiledGold = returnArray;
        returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.silverrewards);
        this.compiledSilver = returnArray;
        returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.fishingReward);
        this.compiledFishing = returnArray;
        returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.eventCommonReward);
        this.compiledEventC = returnArray;
        returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.eventUncommonReward);
        this.compiledEventB = returnArray;
        returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.eventRareReward);
        this.compiledEventA = returnArray;
        returnArray = new ArrayList<Integer>();
        this.processRewards(returnArray, GameConstants.eventSuperReward);
        this.compiledEvent = returnArray;
    }
    
    private void processRewards(final List<Integer> returnArray, final int[] list) {
        int lastitem = 0;
        for (int i = 0; i < list.length; ++i) {
            if (i % 2 == 0) {
                lastitem = list[i];
            }
            else {
                for (int j = 0; j < list[i]; ++j) {
                    returnArray.add(lastitem);
                }
            }
        }
        Collections.shuffle(returnArray);
    }
    
    public int getGoldBoxReward() {
        return this.compiledGold.get(Randomizer.nextInt(this.compiledGold.size()));
    }
    
    public int getSilverBoxReward() {
        return this.compiledSilver.get(Randomizer.nextInt(this.compiledSilver.size()));
    }
    
    public int getFishingReward() {
        return this.compiledFishing.get(Randomizer.nextInt(this.compiledFishing.size()));
    }
    
    public int getEventReward() {
        final int chance = Randomizer.nextInt(100);
        if (chance < 50) {
            return this.compiledEventC.get(Randomizer.nextInt(this.compiledEventC.size()));
        }
        if (chance < 80) {
            return this.compiledEventB.get(Randomizer.nextInt(this.compiledEventB.size()));
        }
        if (chance < 95) {
            return this.compiledEventA.get(Randomizer.nextInt(this.compiledEventA.size()));
        }
        return this.compiledEvent.get(Randomizer.nextInt(this.compiledEvent.size()));
    }
    
    static {
        instance = new RandomRewards();
    }
}
