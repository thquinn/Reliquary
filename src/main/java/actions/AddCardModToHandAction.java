package actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddCardModToHandAction extends AbstractGameAction {
    AbstractCardModifier mod;
    Queue<AbstractCard> cards;
    int waitFrames;
    int nRandom = -1;
    Predicate<AbstractCard> predicate;

    public AddCardModToHandAction(AbstractCardModifier mod) {
        this.mod = mod;
        actionType = ActionType.SPECIAL;
    }
    public AddCardModToHandAction(AbstractCardModifier mod, int nRandom) {
        this.mod = mod;
        actionType = ActionType.SPECIAL;
        this.nRandom = nRandom;
    }
    public AddCardModToHandAction(AbstractCardModifier mod, int nRandom, Predicate<AbstractCard> predicate) {
        this.mod = mod;
        actionType = ActionType.SPECIAL;
        this.nRandom = nRandom;
        this.predicate = predicate;
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
            if (nRandom == 0) {
                isDone = true;
                return;
            }
            List<AbstractCard> handCopy = new ArrayList(AbstractDungeon.player.hand.group);
            if (predicate != null) {
                handCopy = handCopy.stream().filter(predicate).collect(Collectors.toList());
            }
            if (nRandom > 0) {
                Collections.shuffle(handCopy, new Random(AbstractDungeon.miscRng.randomLong()));
                handCopy = handCopy.subList(0, Math.min(nRandom, handCopy.size()));
            }
            cards = new LinkedList<>(handCopy);
        }
        AbstractCard card = cards.remove();
        if (mod.shouldApply(card)) {
            CardModifierManager.addModifier(card, mod.makeCopy());
            card.flash();
            waitFrames = 1;
        }
        if (cards.isEmpty()) {
            isDone = true;
        }
    }
}
