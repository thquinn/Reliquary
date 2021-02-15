package util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class StaticHelpers {
    public static boolean CanClickRelic(AbstractRelic relic) {
        return relic.isObtained &&
               AbstractDungeon.getCurrRoom() != null &&
               AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
               AbstractDungeon.overlayMenu.endTurnButton.enabled &&
               AbstractDungeon.actionManager.actions.isEmpty() &&
               AbstractDungeon.actionManager.currentAction == null;
    }
}
