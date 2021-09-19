package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SkeletonKeyStormOfSteelAction extends AbstractGameAction {
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int n = p.hand.size();
        AbstractCard s = new Shiv().makeCopy();
        s.upgrade();
        s.upgrade();
        addToTop(new MakeTempCardInHandAction(s, n));
        addToTop(new DiscardAction(p, p, n, false));
        isDone = true;
    }
}
