package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MultiplyBlockAction extends AbstractGameAction {
    int multiplier;

    public MultiplyBlockAction(AbstractCreature target, int multiplier) {
        duration = 0.5F;
        actionType = AbstractGameAction.ActionType.BLOCK;
        this.target = target;
        this.multiplier = multiplier;
    }

    public void update() {
        if (duration == 0.5F && target != null && target.currentBlock > 0) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
            target.addBlock(target.currentBlock * (multiplier - 1));
        }
        tickDuration();
    }
}
