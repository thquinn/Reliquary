package patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicSilkGlove;
import relics.RelicStiletto;
import util.ReliquaryLogger;

@SpirePatch(
        clz= AbstractCreature.class,
        method="addBlock"
)
public class PatchStiletto {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"tmp"}
    )
    public static void Insert(AbstractCreature __instance, int blockAmount, @ByRef float[] tmp) {
        AbstractPlayer p = AbstractDungeon.player;
        if (__instance == p) {
            return;
        }
        RelicStiletto stiletto = (RelicStiletto) p.getRelic(RelicStiletto.ID);
        if (stiletto != null) {
            if (tmp[0] >= 1) {
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(__instance, stiletto));
            }
            tmp[0] /= 2;
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}