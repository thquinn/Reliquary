package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import util.ReliquaryLogger;

public class CardModPaperSnowflake extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModPaperSnowflake";

    int amount;

    public CardModPaperSnowflake(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return card.cost >= amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.updateCost(-amount);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.updateCost(amount);
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
