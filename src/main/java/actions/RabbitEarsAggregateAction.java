package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RabbitEarsAggregateAction extends AbstractGameAction {
    private int divideAmount;

    public RabbitEarsAggregateAction(int divideAmount) {
        this.divideAmount = divideAmount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int count = p.drawPile.size() + p.discardPile.size();
        p.gainEnergy(count / divideAmount);
        isDone = true;
    }
}
