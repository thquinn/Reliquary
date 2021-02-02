package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OuijaBoardQueueXAction extends AbstractGameAction {
    private AbstractCard card;
    private int energyOnUse;

    public OuijaBoardQueueXAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        if (this.card == null) {
            if (!queueContainsEndTurnCard())
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem());
        } else if (!queueContains(card)) {
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, true, card.energyOnUse, true, true), false);
        }
        isDone = true;
    }

    private boolean queueContains(AbstractCard card) {
        for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
            if (i.card == card)
                return true;
        }
        return false;
    }

    private boolean queueContainsEndTurnCard() {
        for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
            if (i.card == null)
                return true;
        }
        return false;
    }
}