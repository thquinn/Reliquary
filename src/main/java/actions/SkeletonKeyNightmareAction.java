package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NightmarePower;

public class SkeletonKeyNightmareAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("CopyAction").TEXT;

    boolean firstFrame;
    AbstractPlayer p;

    public SkeletonKeyNightmareAction(int amount) {
        this.amount = amount;
        firstFrame = true;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (firstFrame) {
            firstUpdate();
            firstFrame = false;
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard card = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            applyPower(card);
            AbstractDungeon.player.hand.addToHand(card);
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
    }

    public void firstUpdate() {
        if (p.hand.isEmpty()) {
            isDone = true;
            return;
        }
        if (p.hand.size() == 1) {
            applyPower(p.hand.getBottomCard());
        }
        AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
    }

    public void applyPower(AbstractCard card) {
        card = card.makeStatEquivalentCopy();
        if (card.canUpgrade()) {
            card.upgrade();
        }
        addToTop(new ApplyPowerAction(p, p, new NightmarePower(p, amount, card)));
        isDone = true;
    }
}
