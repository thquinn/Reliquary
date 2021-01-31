package actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import cardmods.CardModRetain;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import util.ReliquaryLogger;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class AddCardModToHandAction extends AbstractGameAction {
    AbstractCardModifier mod;
    Queue<AbstractCard> cards;
    int waitFrames;

    public AddCardModToHandAction(AbstractCardModifier mod) {
        this.mod = mod;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (waitFrames > 0) {
            waitFrames--;
            return;
        }
        if (cards == null) {
            if (AbstractDungeon.player.hand.isEmpty()) {
                isDone = true;
                return;
            }
            cards = new LinkedList<>(AbstractDungeon.player.hand.group);
        }
        AbstractCard card = cards.remove();
        if (mod.shouldApply(card)) {
            CardModifierManager.addModifier(card, mod);
            card.flash();
            waitFrames = 1;
        }
        if (cards.isEmpty()) {
            isDone = true;
        }
    }
}
