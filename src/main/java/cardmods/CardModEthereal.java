package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class CardModEthereal extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModEthereal";

    public CardModEthereal() {
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
        card.selfRetain = false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ID).TEXT[0] + rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModEthereal();
    }
}
