package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.RelicKnifeBlock;

@SpirePatch(
        clz= MakeTempCardInHandAction.class,
        method="update"
)
public class PatchKnifeBlock {
    public static void Prefix(MakeTempCardInHandAction __instance, AbstractCard ___c) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractRelic knifeBlock = AbstractDungeon.player.getRelic(RelicKnifeBlock.ID);
        if (knifeBlock != null && ___c.cardID.equals(Shiv.ID) && __instance.amount > 0) {
            __instance.amount += p.relics.stream().filter(r -> r.relicId.equals(RelicKnifeBlock.ID)).count();
            knifeBlock.flash();
        }
    }
}