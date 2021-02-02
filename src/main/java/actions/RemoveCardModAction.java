package actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveCardModAction extends AbstractGameAction {
    AbstractCard card;
    AbstractCardModifier mod;

    public RemoveCardModAction(AbstractCard card, AbstractCardModifier mod) {
        this.card = card;
        this.mod = mod;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        isDone = true;
        CardModifierManager.removeSpecificModifier(card, mod, false);
    }
}
