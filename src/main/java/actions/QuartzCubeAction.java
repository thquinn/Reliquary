package actions;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.InvincibleTurnsPower;
import relics.RelicQuartzCube;

public class QuartzCubeAction extends AbstractGameAction {
    AbstractMonster target;

    public QuartzCubeAction(AbstractMonster target) {
        this.target = target;
    }

    public void update() {
        isDone = true;
        if (target.hasPower("Artifact")) {
            addToTop(new TriggerArtifactAction(target));
            return;
        }
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new InvincibleTurnsPower(target, RelicQuartzCube.DURATION)));
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new StunMonsterPower(target, RelicQuartzCube.DURATION)));
    }
}
