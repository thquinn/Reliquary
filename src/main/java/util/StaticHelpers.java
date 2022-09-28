package util;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    public static void duplicatePlayedCard(AbstractCard card, AbstractMonster m) {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = Settings.WIDTH / 2f - 300f * Settings.scale;
        tmp.target_y = Settings.HEIGHT / 2f;
        if (m != null) {
            tmp.calculateCardDamage(m);
        }
        tmp.applyPowers();
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }
}
