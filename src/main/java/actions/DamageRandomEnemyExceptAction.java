package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DamageRandomEnemyExceptAction extends AbstractGameAction {
    private AbstractMonster except;
    public AbstractRelic relic;

    public DamageRandomEnemyExceptAction(AbstractMonster except, int damage, DamageInfo.DamageType damageType, AttackEffect effect) {
        actionType = ActionType.DAMAGE;
        this.except = except;
        amount = damage;
        this.damageType = damageType;
        attackEffect = effect;
    }

    public void update() {
        target = AbstractDungeon.getMonsters().getRandomMonster(except, true, AbstractDungeon.cardRandomRng);
        if (target != null) {
            addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, amount, damageType), attackEffect));
            if (relic != null) {
                addToBot(new RelicAboveCreatureAction(target, relic));
            }
        }
        this.isDone = true;
    }
}
