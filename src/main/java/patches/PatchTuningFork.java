package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicTuningFork;

@SpirePatch(
        clz= AbstractMonster.class,
        method="damage"
)
public class PatchTuningFork {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"damageAmount"}
    )
    public static void Insert(AbstractMonster __instance, DamageInfo info, int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (info.owner != p) {
            return;
        }
        RelicTuningFork tuningFork = (RelicTuningFork) p.getRelic(RelicTuningFork.ID);
        if (tuningFork != null) {
            tuningFork.onAttackBeforeBlock(__instance, damageAmount);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "decrementBlock");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}