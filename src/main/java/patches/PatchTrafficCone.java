package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicTrafficCone;

@SpirePatch(
        clz= GameActionManager.class,
        method="getNextAction"
)
public class PatchTrafficCone {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"m"}
    )
    public static void Insert(GameActionManager __instance, AbstractMonster m) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic.relicId.equals(RelicTrafficCone.ID)) {
                ((RelicTrafficCone)relic).triggerIfFirstTime(m);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "applyTurnPowers");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}