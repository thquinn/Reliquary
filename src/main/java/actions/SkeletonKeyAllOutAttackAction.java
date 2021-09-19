package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.AllOutAttack;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SkeletonKeyAllOutAttackAction extends FollowUpAction {
    AllOutAttack allOutAttack;

    public SkeletonKeyAllOutAttackAction(AllOutAttack allOutAttack) {
        this.allOutAttack = allOutAttack;
    }

    @Override
    public void followUp(AbstractGameAction action) {
        BetterDiscardAction discardAction = (BetterDiscardAction) action;
        if (discardAction.discarded.stream().noneMatch(c -> c.type == AbstractCard.CardType.SKILL)) {
            isDone = true;
        }
    }

    @Override
    public void update() {
        addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, allOutAttack.multiDamage, allOutAttack.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        isDone = true;
    }
}
