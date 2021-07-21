package actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import java.util.function.Predicate;

public class PreferentialDrawCardAction extends AbstractGameAction {
    Predicate<AbstractCard> predicate;

    public PreferentialDrawCardAction(int amount, Predicate<AbstractCard> predicate) {
        this.amount = amount;
        this.predicate = predicate;
    }

    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        if (amount <= 0) {
            return;
        }
        AbstractPower noDrawPower = p.getPower(NoDrawPower.POWER_ID);
        if (noDrawPower != null) {
            noDrawPower.flash();
            return;
        }
        int matches = (int) p.drawPile.group.stream().filter(predicate).count();
        int normalDraws = Math.min(amount - matches, p.drawPile.size());
        if (normalDraws > 0) {
            if (amount <= p.drawPile.size()) {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(normalDraws));
            } else {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(normalDraws, new PreferentialDrawCardAction(amount - p.drawPile.size(), predicate), false));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            }
        }
        AbstractDungeon.actionManager.addToTop(new MoveCardsAction(p.hand, p.drawPile, predicate, Math.min(matches, amount)));
    }
}
