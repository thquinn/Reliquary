package patches;

import actions.MoveOrbsLeftAction;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.AlternateCardCosts;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Fusion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.PaperFrog;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.HookedPower;
import powers.MrofOhcePower;
import relics.RelicFishingReel;
import relics.RelicSkeletonKey;
import relics.RelicSpireOfHannoy;
import util.ReliquaryLogger;

public class PatchFishingReel {
    static int SAVED_ENERGY;

    @SpirePatch(
            clz= EnergyManager.class,
            method="recharge"
    )
    public static class PatchFishingReelRechargePrefix {
        public static void Prefix(EnergyManager __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractPower hookedPower = AbstractDungeon.player.getPower(HookedPower.POWER_ID);
            if (hookedPower != null) {
                SAVED_ENERGY = __instance.energy;
                __instance.energy = Math.max(0, __instance.energy - hookedPower.amount);
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, HookedPower.POWER_ID, __instance.energyMaster));
            }
        }
    }

    @SpirePatch(
            clz= EnergyManager.class,
            method="recharge"
    )
    public static class PatchFishingReelRechargePostfix {
        public static void Postfix(EnergyManager __instance) {
            AbstractPower hookedPower = AbstractDungeon.player.getPower(HookedPower.POWER_ID);
            if (hookedPower != null) {
                __instance.energy = SAVED_ENERGY;
            }
        }
    }
}