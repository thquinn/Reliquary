package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.RelicIvoryTrinket;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="useCard"
)
public class PatchAfterUseCard {
    public interface AfterUseCardRelic {
        public void afterUseCard(AbstractCard c, AbstractMonster m);
    }

    public static void Postfix(AbstractPlayer __instance, AbstractCard c, AbstractMonster m, int energyOnUse) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof AfterUseCardRelic) {
                ((AfterUseCardRelic) r).afterUseCard(c, m);
            }
        }
    }
}