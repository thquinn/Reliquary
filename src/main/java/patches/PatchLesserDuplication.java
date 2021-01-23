package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import powers.LesserDuplicationPower;

@SpirePatch(
        clz= DuplicationPower.class,
        method="onUseCard"
)
public class PatchLesserDuplication {
    public static SpireReturn Prefix(DuplicationPower __instance, AbstractCard card, UseCardAction action) {
        if (card.costForTurn < 2 && AbstractDungeon.player.hasPower(LesserDuplicationPower.POWER_ID)) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}