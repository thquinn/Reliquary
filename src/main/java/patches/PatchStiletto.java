package patches;

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
        clz= AbstractMonster.class,
        method="damage"
)
public class PatchStiletto {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"damageAmount"}
    )
    public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (info.owner != p || !p.hasRelic(RelicStiletto.ID)) {
            return;
        }
        int blockBroken = Math.min(damageAmount[0] * 2, __instance.currentBlock);
        if (blockBroken > 0) {
            p.getRelic(RelicStiletto.ID).flash();
        }
        damageAmount[0] += blockBroken / 2;
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "decrementBlock");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}