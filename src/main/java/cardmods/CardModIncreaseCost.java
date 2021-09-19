package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardModIncreaseCost extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModIncreaseCost";

    int amount;

    public CardModIncreaseCost(int amount) {
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
    public String identifier(AbstractCard card) {
        return ID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModIncreaseCost(amount);
    }
}
