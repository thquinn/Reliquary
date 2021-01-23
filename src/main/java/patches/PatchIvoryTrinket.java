package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relics.RelicIvoryTrinket;

@SpirePatch(
        clz= RestoreRetainedCardsAction.class,
        method="update"
)
public class PatchIvoryTrinket {
    public static void Postfix() {
        RelicIvoryTrinket ivoryTrinket = (RelicIvoryTrinket) AbstractDungeon.player.getRelic(RelicIvoryTrinket.ID);
        if (ivoryTrinket != null) {
            ivoryTrinket.onRetain();
        }
    }
}