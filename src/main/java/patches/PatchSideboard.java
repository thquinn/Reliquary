package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicSideboard;
import vfx.FastCardToSideboardEffect;

@SpirePatch(
        clz= CardRewardScreen.class,
        method="acquireCard"
)
public class PatchSideboard {
    @SpireInsertPatch(
            locator= Locator.class
    )
    public static SpireReturn Insert(CardRewardScreen __instance, AbstractCard hoveredCard) {
        RelicSideboard sideboard = (RelicSideboard) AbstractDungeon.player.getRelic(RelicSideboard.ID);
        if (sideboard != null && sideboard.isEnabled()) {
            AbstractDungeon.effectsQueue.add(new FastCardToSideboardEffect(hoveredCard, hoveredCard.current_x, hoveredCard.current_y));
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectsQueue");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}