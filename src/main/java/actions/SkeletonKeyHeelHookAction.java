package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SkeletonKeyHeelHookAction extends AbstractGameAction {
    DamageInfo info;

    public SkeletonKeyHeelHookAction(AbstractCreature target, DamageInfo info) {
        this.target = target;
        this.info = info;
    }

    @Override
    public void update() {
        if (target != null && (target.hasPower(WeakPower.POWER_ID) || target.hasPower(PoisonPower.POWER_ID))) {
            addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            addToTop(new GainEnergyAction(1));
        }
        addToTop(new DamageAction(target, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        isDone = true;
    }
}
