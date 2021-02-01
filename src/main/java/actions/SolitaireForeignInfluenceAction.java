package actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class SolitaireForeignInfluenceAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean upgraded;

    public SolitaireForeignInfluenceAction(int amount, boolean upgraded) {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.upgraded = upgraded;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if (!retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                int handCount = AbstractDungeon.player.hand.size();
                for (int i = 0; i < amount; i++) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (upgraded)
                        disCard.setCostForTurn(0);
                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (handCount < BaseMod.MAX_HAND_SIZE) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        handCount++;
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    }
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        while (derp.size() != 3) {
            AbstractCard.CardRarity cardRarity;
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(99);
            if (roll < 55) {
                cardRarity = AbstractCard.CardRarity.COMMON;
            } else if (roll < 85) {
                cardRarity = AbstractCard.CardRarity.UNCOMMON;
            } else {
                cardRarity = AbstractCard.CardRarity.RARE;
            }
            AbstractCard tmp = CardLibrary.getAnyColorCard(AbstractCard.CardType.ATTACK, cardRarity);
            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe)
                derp.add(tmp.makeCopy());
        }
        return derp;
    }
}