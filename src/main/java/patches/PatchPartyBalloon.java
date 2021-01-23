package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicPartyBalloon;

import java.util.Collections;

@SpirePatch(
        clz= CardGroup.class,
        method="initializeDeck"
)
public class PatchPartyBalloon {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"copy"}
    )
    public static void Insert(CardGroup __instance, CardGroup masterDeck, CardGroup copy) {
        if (!AbstractDungeon.player.hasRelic(RelicPartyBalloon.ID)) {
            return;
        }
        // Put all powers on top of the deck.
        int operations = 0;
        for (int i = 0; i < copy.size() - 1; i++) {
            operations++;
            if (operations > copy.size()) {
                // The entire deck from this point is stuff we've already put on top.
                break;
            }
            AbstractCard card = copy.group.get(i);
            if (card.type == AbstractCard.CardType.POWER) {
                copy.group.remove(i);
                copy.addToTop(card);
                i--;
            }
        }
        // Shuffle the top half of the deck.
        Collections.shuffle(copy.group.subList((int)Math.ceil(copy.group.size() / 2f), copy.group.size()));
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(CardGroup.class, "group");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}