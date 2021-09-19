package actions;

import basemod.helpers.CardModifierManager;
import cardmods.CardModShuffleBackOnce;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import relics.RelicOuijaBoard;

public class OuijaBoardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("WishAction");
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(RelicOuijaBoard.ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;

    int costChange;

    public OuijaBoardAction(int costChange) {
        this.costChange = costChange;
        setValues(null, AbstractDungeon.player, amount);
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        RelicOuijaBoard ouija = (RelicOuijaBoard) p.getRelic(RelicOuijaBoard.ID);
        if (duration == Settings.ACTION_DUR_FASTER) {
            if (p.discardPile.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(ouija.validTargets, 1, TEXT[0], false, false, true, false);
            AbstractDungeon.overlayMenu.cancelButton.show(DESCRIPTIONS[4]);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                PlayToDrawPile(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
            isDone = true;
        } else if (!AbstractDungeon.isScreenUp) {
            ouija.CanUseThisTurn();
            isDone = true;
        }
        tickDuration();
    }

    void PlayToDrawPile(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        p.discardPile.removeCard(card);
        p.limbo.addToBottom(card);
        CardModifierManager.addModifier(card, new CardModShuffleBackOnce());
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        if (card.cost == -1) {
            card.energyOnUse = EnergyPanel.totalCount - 1;
            p.loseEnergy(EnergyPanel.totalCount);
            addToTop(new OuijaBoardQueueXAction(card));
        } else {
            card.setCostForTurn(card.costForTurn + costChange);
            p.loseEnergy(card.costForTurn);
            addToTop(new NewQueueCardAction(card, true, false, true));
        }
        addToTop(new UnlimboAction(card));
    }
}
