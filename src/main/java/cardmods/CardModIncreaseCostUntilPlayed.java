package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardModIncreaseCostUntilPlayed extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModIncreaseCostUntilPlayed";

    int amount;

    public CardModIncreaseCostUntilPlayed(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return card.cost != -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.freeToPlayOnce) {
            card.freeToPlayOnce = false;
            card.setCostForTurn(1);
        } else {
            card.updateCost(amount);
        }
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        super.onRemove(card);
        card.updateCost(-amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModIncreaseCostUntilPlayed(amount);
    }
}
