package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ApplyPowerIfAbsentAction extends AbstractGameAction {
    AbstractCreature target, source;
    AbstractPower power;
    AbstractRelic relicToShow;

    public ApplyPowerIfAbsentAction(AbstractCreature target, AbstractCreature source, AbstractPower power) {
        this.target = target;
        this.source = source;
        this.power = power;
        actionType = ActionType.POWER;
    }
    public ApplyPowerIfAbsentAction(AbstractCreature target, AbstractCreature source, AbstractPower power, AbstractRelic relicToShow) {
        this.target = target;
        this.source = source;
        this.power = power;
        this.relicToShow = relicToShow;
        actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        if (!target.hasPower(power.ID)) {
            addToTop(new ApplyPowerAction(target, source, power));
            if (relicToShow != null) {
                addToTop(new RelicAboveCreatureAction(target, relicToShow));
            }
        }
        isDone = true;
    }
}
