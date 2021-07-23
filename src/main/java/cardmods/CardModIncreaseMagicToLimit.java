package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardModIncreaseMagicToLimit extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModIncreaseMagicToLimit";

    int amount, limit;

    public CardModIncreaseMagicToLimit(int amount, int limit) {
        this.amount = amount;
        this.limit = limit;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return card.baseMagicNumber < limit;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.baseMagicNumber = Math.min(card.baseMagicNumber + amount, limit);
        card.magicNumber = Math.min(card.magicNumber + amount, limit);
        card.flash();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModIncreaseMagicToLimit(amount, limit);
    }
}
