package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class CardModRetain extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModRetain";

    public CardModRetain() {
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.selfRetain && !card.isEthereal;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ID).TEXT[0] + rawDescription;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModRetain();
    }
}
