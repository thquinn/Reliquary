package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class CardModIridiumCopy extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModIridiumCopy";

    public CardModIridiumCopy() {
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModIridiumCopy();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
