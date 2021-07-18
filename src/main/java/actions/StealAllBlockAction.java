package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StealAllBlockAction extends AbstractGameAction {
    public StealAllBlockAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
    }

    public void update() {
        isDone = true;
        if (!target.isDying && !target.isDead && target.currentBlock > 0) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(source, target.currentBlock));
            target.loseBlock();
        }
    }
}
