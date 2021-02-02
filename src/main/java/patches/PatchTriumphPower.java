package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.DivinityStance;
import powers.TriumphPower;

@SpirePatch(
        clz= DivinityStance.class,
        method="atDamageGive"
)
public class PatchTriumphPower {
    public static SpireReturn<Float> Prefix(DivinityStance __instance, float damage, DamageInfo.DamageType type) {
        TriumphPower triumph = (TriumphPower) AbstractDungeon.player.getPower(TriumphPower.POWER_ID);
        if (triumph != null && type == DamageInfo.DamageType.NORMAL) {
            return SpireReturn.Return(damage * (3 + triumph.amount));
        }
        return SpireReturn.Continue();
    }
}