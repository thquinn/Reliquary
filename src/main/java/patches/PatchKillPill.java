package patches;

import basemod.devcommands.relic.Relic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.*;
import relics.RelicKillPill;
import relics.RelicRadioactivePellet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if (instance.amount <= 0) return;
        int damage = instance.amount;
        List<AbstractGameAction> actions = new ArrayList<>();
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic.relicId.equals(RelicKillPill.ID)) {
                actions.add(new RelicAboveCreatureAction(instance.owner, relic));
                actions.add(new PoisonLoseHpAction(instance.owner, source, damage, AbstractGameAction.AttackEffect.POISON));
                if (!AbstractDungeon.player.hasRelic(RelicRadioactivePellet.ID)) {
                    damage--;
                }
            }
        }
        // Put the actions on top in reverse order.
        Collections.reverse(actions);
        for (AbstractGameAction action : actions) {
            AbstractDungeon.actionManager.addToTop(action);
        }
    }
}