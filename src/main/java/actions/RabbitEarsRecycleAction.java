package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RabbitEarsRecycleAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p = AbstractDungeon.player;
    public int amount;
    boolean triggered;

    public RabbitEarsRecycleAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        if (!triggered) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, true, false, false, true);
            triggered = true;
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.costForTurn == -1) {
                    addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
                } else if (c.costForTurn > 0) {
                    addToTop(new GainEnergyAction(c.costForTurn));
                }
                this.p.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }
    }
}
