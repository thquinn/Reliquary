package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RabbitEarsForTheEyesAction extends AbstractGameAction {
    private AbstractMonster m;

    public RabbitEarsForTheEyesAction(int amount, AbstractMonster m) {
        this.amount = amount;
        this.m = m;
    }

    public void update() {
        isDone = true;
        if (m != null && m.getIntentBaseDmg() <= 0) {
            addToTop(new GainEnergyAction(amount));
        }
    }
}
