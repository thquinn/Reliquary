package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicGummyVitamins;

@SpirePatch(
        clz= DrawCardAction.class,
        method="update"
)
public class PatchGummyVitamins {
    @SpireInsertPatch(
            locator= Locator.class
    )
    public static void Insert(DrawCardAction __instance) {
        RelicGummyVitamins gummyVitamins = (RelicGummyVitamins) AbstractDungeon.player.getRelic(RelicGummyVitamins.ID);
        if (gummyVitamins != null && gummyVitamins.drawCardAction == __instance) {
            gummyVitamins.drawSuccessFollowUp();
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "draw");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}