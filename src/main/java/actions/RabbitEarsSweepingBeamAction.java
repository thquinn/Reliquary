package actions;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RabbitEarsSweepingBeamAction extends DamageAllEnemiesAction {
    private boolean firstFrame = true;

    public RabbitEarsSweepingBeamAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AttackEffect effect) {
        super(source, amount, type, effect, false);
    }

    @Override
    public void update() {
        if (firstFrame) {
            for (int i = 0; i < damage.length; i++) {
                damage[i] += EnergyPanel.totalCount;
            }
            firstFrame = false;
        }
        super.update();
    }
}
