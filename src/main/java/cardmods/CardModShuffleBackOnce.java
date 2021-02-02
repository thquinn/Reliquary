package cardmods;

import actions.RemoveCardModAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardModShuffleBackOnce extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModShuffleBackOnce";

    boolean removing = false;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.shuffleBackIntoDrawPile = true;
    }

    @Override
    public void onUpdate(AbstractCard card) {
        if (!removing && AbstractDungeon.player.hand.contains(card)) {
            AbstractDungeon.actionManager.addToTop(new RemoveCardModAction(card, this));
            removing = true;
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.shuffleBackIntoDrawPile = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModShuffleBackOnce();
    }
}
