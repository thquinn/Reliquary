package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicRadioactivePellet;

@SpirePatch(
        clz= PoisonLoseHpAction.class,
        method="update"
)
public class PatchRadioactivePellet {
    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"p"}
    )
    public static void Insert(AbstractPower p) {
        if (AbstractDungeon.player.hasRelic(RelicRadioactivePellet.ID)) {
            p.amount++;
            AbstractDungeon.player.getRelic(RelicRadioactivePellet.ID).flash();
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPower.class, "amount");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}