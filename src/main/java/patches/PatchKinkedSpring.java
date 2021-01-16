package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relics.RelicKinkedSpring;

public class PatchKinkedSpring {
    @SpirePatch(
            clz= DrawCardAction.class,
            method="update"
    )
    public static class PatchNoMidturnShuffleDrawCardAction {
        public static void Prefix(DrawCardAction __instance) {
            if (AbstractDungeon.player.hasRelic(RelicKinkedSpring.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > 0) {
                int drawSize = AbstractDungeon.player.drawPile.size();
                if (__instance.amount > drawSize) {
                    __instance.amount = drawSize;
                    AbstractDungeon.player.getRelic(RelicKinkedSpring.ID).flash();
                }
            }
        }
    }

    @SpirePatch(
            clz= FastDrawCardAction.class,
            method="update"
    )
    public static class PatchNoMidturnShuffleFastDrawCardAction {
        public static void Prefix(FastDrawCardAction __instance) {
            if (AbstractDungeon.player.hasRelic(RelicKinkedSpring.ID) && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > 0) {
                int drawSize = AbstractDungeon.player.drawPile.size();
                if (__instance.amount > drawSize) {
                    __instance.amount = drawSize;
                    AbstractDungeon.player.getRelic(RelicKinkedSpring.ID).flash();
                }
            }
        }
    }
}