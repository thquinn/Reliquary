package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import powers.HydratedPower;

@SpirePatch(
        clz= LoseStrengthPower.class,
        method="atEndOfTurn"
)
public class PatchHydrated {
    public static SpireReturn Prefix(LoseStrengthPower __instance) {
        HydratedPower hydratedPower = (HydratedPower) __instance.owner.getPower(HydratedPower.POWER_ID);
        if (hydratedPower != null) {
            hydratedPower.flash();
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(__instance.owner, __instance.owner, HydratedPower.POWER_ID, 1));
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}