package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import javassist.*;
import relics.RelicKillPill;

public class PatchKillPill {
    @SpirePatch(
            clz = PoisonPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchKillPillConstructor {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();

            CtMethod method = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "onInitialApplication", // Method name
                    new CtClass[]{}, // Parameters
                    null, // Exceptions
                    "{" +
                        "patches.PatchKillPill.Impl(this, this.source);" +
                    "}",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = PoisonPower.class,
            method = "stackPower"
    )
    public static class PatchKillPillStack {
        public static void Postfix(PoisonPower __instance, int stackAmount, AbstractCreature ___source) {
            Impl(__instance, ___source);
        }
    }

    public static void Impl(PoisonPower instance, AbstractCreature source) {
        RelicKillPill killPill = (RelicKillPill) AbstractDungeon.player.getRelic(RelicKillPill.ID);
        if (killPill != null && instance.amount > 0) {
            AbstractDungeon.actionManager.addToTop(new PoisonLoseHpAction(instance.owner, source, instance.amount, AbstractGameAction.AttackEffect.POISON));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(instance.owner, killPill));
        }
    }
}