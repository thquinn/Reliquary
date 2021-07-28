package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import powers.HookedPower;

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