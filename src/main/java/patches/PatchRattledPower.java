package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.RattledPower;

public class PatchRattledPower {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="damage"
    )
    public static class PatchRattledPowerPlayer {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"p", "damageAmount"}
        )
        public static void Insert(AbstractPlayer __instance, DamageInfo info, AbstractPower p, @ByRef int[] damageAmount) {
            if (p.ID.equals(RattledPower.POWER_ID)) {
                damageAmount[0] = ((RattledPower)p).onLoseHpWithInfo(info, damageAmount[0]);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPower.class, "onLoseHp");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= AbstractMonster.class,
            method="damage"
    )
    public static class PatchRattledPowerMonster {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"damageAmount"}
        )
        public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : __instance.powers) {
                if (p.ID.equals(RattledPower.POWER_ID)) {
                    damageAmount[0] = ((RattledPower)p).onLoseHpWithInfo(info, damageAmount[0]);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "powers");
                int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
                return new int[]{matches[1]};
            }
        }
    }
}