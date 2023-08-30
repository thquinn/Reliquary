package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import powers.TauntPower;

public class ApplyTauntAction extends AbstractGameAction {
    AbstractCreature target;
    AbstractRelic relic;

    public ApplyTauntAction(AbstractCreature target, AbstractRelic relic) {
        this.target = target;
        this.relic = relic;
        actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        isDone = true;
        if (TauntPower.getTaunter() != null) {
            return;
        }
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new TauntPower(target)));
        addToTop(new RelicAboveCreatureAction(target, relic));
    }
}
