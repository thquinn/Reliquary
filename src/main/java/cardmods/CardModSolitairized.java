package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardModSolitairized extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModSolitairized";

    public CardModSolitairized() {
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModSolitairized();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
