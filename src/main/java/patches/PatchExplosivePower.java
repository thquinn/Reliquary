package patches;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.powers.ExplosivePower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicVitrine;

import java.util.HashSet;
import java.util.UUID;

@SpirePatch(
        clz= ExplosivePower.class,
        method="duringTurn"
)
public class PatchExplosivePower {
    @SpireInsertPatch(
            locator= Locator.class
    )
    public static SpireReturn Insert(ExplosivePower __instance) {
        if (__instance.owner.hasPower(StunMonsterPower.POWER_ID)) {
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.NewExprMatcher(ReducePowerAction.class);
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}