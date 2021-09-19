package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

public class BetterDiscardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;

    int amount;
    boolean isRandom;
    FollowUpAction followUpAction;
    public List<AbstractCard> discarded;
    boolean firstFrame;

    public BetterDiscardAction(int amount, boolean isRandom, FollowUpAction followUpAction) {
        this.amount = amount;
        this.isRandom = isRandom;
        this.followUpAction = followUpAction;
        discarded = new ArrayList<>();
        firstFrame = true;
    }

    @Override
    public void update() {
        if (firstFrame) {
            firstUpdate();
            firstFrame = false;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractPlayer p = AbstractDungeon.player;
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToDiscardPile(c);
                discarded.add(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            endWithFollowUp();
        }
    }

    public void firstUpdate() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }

        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.size() <= amount) {
            amount = p.hand.size();
            for (int i = 0; i < amount; i++) {
                AbstractCard c = p.hand.getTopCard();
                p.hand.moveToDiscardPile(c);
                discarded.add(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            AbstractDungeon.player.hand.applyPowers();
            endWithFollowUp();
            return;
        }
        if (isRandom) {
            for (int i = 0; i < amount; i++) {
                AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                p.hand.moveToDiscardPile(c);
                discarded.add(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            endWithFollowUp();
        } else {
            if (amount < 0) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false);
            }
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    void endWithFollowUp() {
        isDone = true;
        if (followUpAction != null) {
            followUpAction.followUp(this);
            addToTop(followUpAction);
        }
    }
}