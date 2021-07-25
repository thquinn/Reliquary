package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relics.RelicBellows;
import vfx.UnfadeOutEffect;

public class BellowsAction extends AbstractGameAction {
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicBellows.ID).DESCRIPTIONS;

    RelicBellows bellows;
    AbstractPlayer p;
    boolean firstFrame;

    public BellowsAction(RelicBellows bellows) {
        this.bellows = bellows;
        actionType = ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        firstFrame = true;
    }

    @Override
    public void update() {
        if (firstFrame) {
            firstUpdate();
            firstFrame = false;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 0) {
            p.exhaustPile.moveToDeck(AbstractDungeon.gridSelectScreen.selectedCards.get(0), true);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            bellows.stopPulse();
            isDone = true;
        }
    }

    public void firstUpdate() {
        if (p.exhaustPile.isEmpty()) {
            isDone = true;
            return;
        }
        if (p.exhaustPile.size() == 1) {
            AbstractCard c = p.exhaustPile.getBottomCard();
            p.exhaustPile.moveToDeck(c, true);
            AbstractDungeon.effectsQueue.add(new UnfadeOutEffect(c)); // c.unfadeOut() isn't enough here for some godforsaken reason
            addToTop(new RelicAboveCreatureAction(p, bellows));
            isDone = true;
            return;
        }
        for (AbstractCard c : p.exhaustPile.group) {
            c.stopGlowing();
            c.unhover();
            c.unfadeOut();
        }
        AbstractDungeon.gridSelectScreen.open(p.exhaustPile, 1, DESCRIPTIONS[2], false);
        bellows.beginLongPulse();
    }
}
