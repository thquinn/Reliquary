package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class RabbitEarsBarrageAction extends AbstractGameAction {
    private DamageInfo perOrbInfo, fullInfo;

    public RabbitEarsBarrageAction(AbstractCreature m, DamageInfo perOrbInfo, DamageInfo fullInfo) {
        this.perOrbInfo = perOrbInfo;
        this.fullInfo = fullInfo;
        target = m;
    }

    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        int orbCount = (int) p.orbs.stream().filter(orb -> !(orb instanceof EmptyOrbSlot)).count();
        if (orbCount == 0) {
            return;
        }
        if (orbCount == p.orbs.size()) {
            // TODO: Find a way to make Strength affect this last hit.
            addToTop(new DamageAction(target, fullInfo, AttackEffect.BLUNT_HEAVY));
        }
        for (int i = 0; i < orbCount; i++) {
            addToTop(new DamageAction(target, perOrbInfo, AttackEffect.BLUNT_LIGHT, true));
        }
    }
}
