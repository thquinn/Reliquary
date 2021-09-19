package util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class StaticHelpers {
    public static boolean canClickRelic(AbstractRelic relic) {
        return relic.isObtained &&
               AbstractDungeon.getCurrRoom() != null &&
               AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
               AbstractDungeon.overlayMenu.endTurnButton.enabled &&
               AbstractDungeon.actionManager.actions.isEmpty() &&
               AbstractDungeon.actionManager.currentAction == null;
    }
    public static void removeStrikeTips(AbstractRelic relic) {
        List<String> strikes = Arrays.stream(GameDictionary.STRIKE.NAMES).map(s -> s.toLowerCase()).collect(Collectors.toList());
        for (Iterator<PowerTip> t = relic.tips.iterator(); t.hasNext(); ) {
            PowerTip derp = t.next();
            String s = derp.header.toLowerCase();
            if (strikes.contains(s)) {
                t.remove();
                break;
            }
        }
    }
}
