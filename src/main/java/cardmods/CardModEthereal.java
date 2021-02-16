package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class CardModEthereal extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModEthereal";

    @Override
    public void onUpdate(AbstractCard card) {
        // doing this here instead of onInitialApplication because of interactions with card upgrades removing Ethereal
        if (!card.isEthereal) {
            card.isEthereal = true;
            card.selfRetain = false;
            card.rawDescription = CardCrawlGame.languagePack.getUIString(ID).TEXT[0] + card.rawDescription;
            card.initializeDescription();
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModEthereal();
    }
}
