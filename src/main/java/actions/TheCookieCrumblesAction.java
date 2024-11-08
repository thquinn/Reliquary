package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import powers.TauntPower;

public class TheCookieCrumblesAction extends AbstractGameAction {
    static int EFFECT_THRESHOLD = 15;

    AbstractCreature target;
    int multiplier;

    public TheCookieCrumblesAction(AbstractCreature target, int multipler) {
        this.target = target;
        this.multiplier = multipler;
        actionType = ActionType.EXHAUST;
    }

    @Override
    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        int count = 1;
        for (AbstractCard card : p.hand.group) {
            addToBot(new ExhaustSpecificCardAction(card, p.hand, true));
            count++;
        }
        for (AbstractCard card : p.drawPile.group) {
            addToBot(new ExhaustSpecificCardAction(card, p.drawPile, true));
            count++;
        }
        for (AbstractCard card : p.discardPile.group) {
            addToBot(new ExhaustSpecificCardAction(card, p.discardPile, true));
            count++;
        }
        if (count >= EFFECT_THRESHOLD) {
            addToBot(new VFXAction(new WeightyImpactEffect(target.hb.cX, target.hb.cY)));
            addToBot(new WaitAction(0.8f));
        }
        AbstractGameAction.AttackEffect effect = count >= EFFECT_THRESHOLD ? AttackEffect.NONE : AbstractGameAction.AttackEffect.FIRE;
        addToBot(new DamageAction(target, new DamageInfo(p, count * multiplier, DamageInfo.DamageType.NORMAL), effect));
    }
}
