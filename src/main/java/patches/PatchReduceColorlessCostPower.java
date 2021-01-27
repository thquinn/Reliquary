package patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.PathVictoryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.ReduceColorlessCostPower;
import relics.RelicKinkedSpring;
import util.ReliquaryLogger;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PatchReduceColorlessCostPower {
    @SpirePatch(
            clz= AbstractCard.class,
            method="getCost"
    )
    public static class PatchReduceColorlessCostPowerGetCost {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static SpireReturn Insert(AbstractCard __instance) {
            if (PatchReduceColorlessCostPower.isEligible(__instance)) {
                return SpireReturn.Return(Integer.toString(__instance.costForTurn - 1));
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "costForTurn");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method="hasEnoughEnergy"
    )
    public static class PatchReduceColorlessCostPowerHasEnoughEnergy {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static SpireReturn Insert(AbstractCard __instance) {
            if (PatchReduceColorlessCostPower.isEligible(__instance) && EnergyPanel.totalCount >= __instance.costForTurn - 1) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "cantUseMessage");
                int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
                return new int[]{matches[matches.length - 1]};
            }
        }
    }


    @SpirePatch(
            clz= AbstractPlayer.class,
            method="useCard"
    )
    public static class PatchReduceColorlessCostPowerUseCard {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
            if (PatchReduceColorlessCostPower.isEligible(c)) {
                c.costForTurn--;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "costForTurn");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method="renderEnergy"
    )
    public static class PatchReduceColorlessCostPowerRenderEnergy {
        @SpireInsertPatch(
                locator= Locator.class,
                localvars = {"costColor"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb, Color ___ENERGY_COST_MODIFIED_COLOR, @ByRef Color[] costColor) {
            if (PatchReduceColorlessCostPower.isEligible(__instance)) {
                costColor[0] = ___ENERGY_COST_MODIFIED_COLOR;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "transparency");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    public static boolean isEligible(AbstractCard card) {
        return AbstractDungeon.player.hasPower(ReduceColorlessCostPower.POWER_ID) && card.color == AbstractCard.CardColor.COLORLESS && card.costForTurn > 0;
    }
}