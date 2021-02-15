package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import javassist.*;
import relics.RelicRustamsPendant;
import stances.AtonementStance;

public class PatchRustamsPendant {
    private static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicRustamsPendant.ID).DESCRIPTIONS;

    @SpirePatch(
            clz= MantraPower.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchRustamsPendantMantra {
        public static void Postfix(MantraPower __instance) {
            if (AbstractDungeon.player.hasRelic(RelicRustamsPendant.ID)) {
                __instance.canGoNegative = true;
            }
        }
    }

    @SpirePatch(
            clz= MantraPower.class,
            method="stackPower"
    )
    public static class PatchRustamsPendantMantraStack {
        public static void Postfix(MantraPower __instance) {
            if (__instance.amount <= -10) {
                AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new AtonementStance()));
                __instance.amount += 10;
            }
            if (__instance.amount == 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, __instance.ID));
            }
        }
    }

    @SpirePatch(
            clz= MantraPower.class,
            method="updateDescription"
    )
    public static class PatchRustamsPendantMantraDescription {
        public static void Postfix(MantraPower __instance) {
            __instance.type = __instance.amount > 0 ? AbstractPower.PowerType.BUFF : AbstractPower.PowerType.DEBUFF;
        }
    }

    @SpirePatch(
            clz= DevotionPower.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchRustamsPendantDevotion {
        public static void Postfix(DevotionPower __instance) {
            if (AbstractDungeon.player.hasRelic(RelicRustamsPendant.ID)) {
                __instance.canGoNegative = true;
            }
        }
    }

    @SpirePatch(
            clz= DevotionPower.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchRustamsPendantDevotionStack {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            CtMethod method = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "stackPower", // Method name
                    new CtClass[]{ CtClass.intType },
                    null, // Exceptions
                    "{" +
                        "super.stackPower($1);" +
                        PatchRustamsPendantDevotionStack.class.getName() + ".removeZeroDevotion(this);" +
                    "}",
                    ctClass
            );
            ctClass.addMethod(method);
        }
        public static void removeZeroDevotion(DevotionPower __instance) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, __instance.ID));
        }
    }

    @SpirePatch(
            clz= DevotionPower.class,
            method="updateDescription"
    )
    public static class PatchRustamsPendantDevotionDescription {
        public static SpireReturn Prefix(DevotionPower __instance) {
            if (__instance.amount < 0) {
                __instance.description = DESCRIPTIONS[7] + -__instance.amount + DESCRIPTIONS[8];
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
