package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class MultiplyPoisonAction extends AbstractGameAction {
    int multiplier;

    public MultiplyPoisonAction(AbstractCreature source, AbstractCreature target, int multiplier) {
        actionType = ActionType.POWER;
        this.source = source;
        this.target = target;
        this.multiplier = multiplier;
    }

    public void update() {
        isDone = true;
        if (!target.hasPower(PoisonPower.POWER_ID)) {
            return;
        }
        int amt = target.getPower(PoisonPower.POWER_ID).amount * (multiplier - 1);
        addToTop(new ApplyPowerAction(target, target, new PoisonPower(target, source, amt), amt));
    }
}
