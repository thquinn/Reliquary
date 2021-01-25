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
    public void onInitialApplication(AbstractCard card) {
        card.updateCost(amount);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModIncreaseCost(amount);
    }
}
