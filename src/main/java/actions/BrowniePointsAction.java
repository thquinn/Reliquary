package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BrowniePointsAction extends AbstractGameAction {
    int healAmount, energyGain;

    public BrowniePointsAction(AbstractCreature target, int healAmount, int energyGain) {
        actionType = ActionType.POWER;
        this.target = target;
        this.healAmount = healAmount;
        this.energyGain = energyGain;
    }

    public void update() {
        isDone = true;
        boolean success = target.maxHealth - target.currentHealth >= healAmount;
        target.heal(healAmount);
        if (success) {
            addToTop(new GainEnergyAction(energyGain));
        }
    }
}
