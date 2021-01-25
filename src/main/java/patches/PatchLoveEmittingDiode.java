package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import relics.RelicLoveEmittingDiode;

public class PatchLoveEmittingDiode {
    @SpirePatch(
            clz = Dark.class,
            method = "applyFocus"
    )
    public static class PatchLoveEmittingDiodeDark {
        public static void Postfix(Dark __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractPower focus = p.getPower(FocusPower.POWER_ID);
            if (focus != null && focus.amount > 0 && p.hasRelic(RelicLoveEmittingDiode.ID) && p.hasOrb() && __instance == p.orbs.get(0)) {
                __instance.passiveAmount = Math.max(0, __instance.passiveAmount + 2 * focus.amount);
            }
        }
    }

    @SpirePatch(
            clz = AbstractOrb.class,
            method = "applyFocus"
    )
    public static class PatchLoveEmittingDiodeOther {
        public static void Postfix(AbstractOrb __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractPower focus = p.getPower(FocusPower.POWER_ID);
            if (focus != null && focus.amount > 0 && p.hasRelic(RelicLoveEmittingDiode.ID) && p.hasOrb() && __instance == p.orbs.get(0) && !__instance.ID.equals(Plasma.ORB_ID)) {
                __instance.passiveAmount = Math.max(0, __instance.passiveAmount + (RelicLoveEmittingDiode.MULTIPLIER - 1) * focus.amount);
                __instance.evokeAmount = Math.max(0, __instance.evokeAmount + (RelicLoveEmittingDiode.MULTIPLIER - 1) * focus.amount);
            }
        }
    }

    @SpirePatch(
            clz = AbstractOrb.class,
            method = "setSlot"
    )
    public static class PatchLoveEmittingDiodeUpdateFocusOnSlotChange {
        public static void Postfix(AbstractOrb __instance) {
            __instance.updateDescription();
        }
    }
}