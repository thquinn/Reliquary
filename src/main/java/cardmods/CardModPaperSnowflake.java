package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vfx.PaperSnowflakeRepickEffect;

public class CardModPaperSnowflake extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModPaperSnowflake";

    int amount;
    int lastUpgrades;

    public CardModPaperSnowflake(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return card.cost > 0;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.updateCost(-amount);
        lastUpgrades = card.timesUpgraded;
    }

    @Override
    public void onUpdate(AbstractCard card) {
        if (card.timesUpgraded > lastUpgrades) {
            AbstractDungeon.effectList.add(new PaperSnowflakeRepickEffect());
        }
        lastUpgrades = card.timesUpgraded;
    }

    @Override
    public void onRemove(AbstractCard card) {
        // Figure out what the cost is supposed to be.
        AbstractCard copy = card.makeCopy();
        for (int i = 0; i < card.timesUpgraded; i++) {
            copy.upgrade();
        }
        card.updateCost(copy.cost - card.cost);
        card.isCostModified = false;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModPaperSnowflake(amount);
    }
}
