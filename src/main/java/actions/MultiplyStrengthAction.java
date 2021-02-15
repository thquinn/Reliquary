package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MultiplyStrengthAction extends AbstractGameAction {
    int multiplier;

    public MultiplyStrengthAction(AbstractCreature target, int multiplier) {
        actionType = ActionType.POWER;
        this.target = target;
        this.multiplier = multiplier;
    }

    public void update() {
        int strAmt = (target.getPower(StrengthPower.POWER_ID)).amount * (multiplier - 1);
        addToTop(new ApplyPowerAction(target, target, new StrengthPower(target, strAmt), strAmt));
        isDone = true;
    }
}
