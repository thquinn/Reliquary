package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BetterDrawPileToHandWithConsumerAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard.CardType typeToCheck;
    private Consumer<List<AbstractCard>> consumer;
    
    public BetterDrawPileToHandWithConsumerAction(int amount, AbstractCard.CardType type, Consumer<List<AbstractCard>> consumer) {
        p = AbstractDungeon.player;
        setValues(p, p, amount);
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_MED;
        typeToCheck = type;
        this.consumer = consumer;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            if (p.drawPile.isEmpty()) {
                isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.drawPile.group) {
                if (c.type == typeToCheck)
                    tmp.addToRandomSpot(c);
            }
            if (tmp.size() == 0) {
                isDone = true;
                return;
            }
            List<AbstractCard> cards = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    AbstractCard card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (p.hand.size() == 10) {
                        p.drawPile.moveToDiscardPile(card);
                        p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                        cards.add(card);
                    }
                }
            }
            if (cards.size() > 0) {
                consumer.accept(cards);
            }
            isDone = true;
        }
        tickDuration();
    }
}