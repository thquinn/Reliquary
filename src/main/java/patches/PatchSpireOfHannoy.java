package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.PaperFrog;
import relics.RelicSpireOfHannoy;

public class PatchSpireOfHannoy {
    @SpirePatch(
            clz= WeakPower.class,
            method="atDamageGive"
    )
    public static class PatchSpireOfHannoyWeak {
        public static SpireReturn<Float> Prefix(WeakPower __instance, float damage, DamageInfo.DamageType type) {
            if (__instance.owner.isPlayer || type != DamageInfo.DamageType.NORMAL || !AbstractDungeon.player.hasRelic(RelicSpireOfHannoy.ID)) {
                return SpireReturn.Continue();
            }
            float multiplier = .75f;
            if (AbstractDungeon.player.hasRelic(PaperCrane.ID)) {
                multiplier -= .15f;
            }
            multiplier -= Math.min(RelicSpireOfHannoy.POWER_PER_STACK * (__instance.amount - 1), RelicSpireOfHannoy.MAX_POWER);
            return SpireReturn.Return(damage * multiplier);
        }
    }

    @SpirePatch(
            clz= VulnerablePower.class,
            method="atDamageReceive"
    )
    public static class PatchSpireOfHannoyVulnerable {
        public static SpireReturn<Float> Prefix(VulnerablePower __instance, float damage, DamageInfo.DamageType type) {
            if (__instance.owner.isPlayer || type != DamageInfo.DamageType.NORMAL || !AbstractDungeon.player.hasRelic(RelicSpireOfHannoy.ID)) {
                return SpireReturn.Continue();
            }
            float multiplier = 1.5f;
            if (AbstractDungeon.player.hasRelic(PaperFrog.ID)) {
                multiplier += .25f;
            }
            multiplier += Math.min(RelicSpireOfHannoy.POWER_PER_STACK * (__instance.amount - 1), RelicSpireOfHannoy.MAX_POWER);
            return SpireReturn.Return(damage * multiplier);
        }
    }
}