package actions;

import cards.cookie.CardCookie;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.InvincibleTurnsPower;
import relics.RelicQuartzCube;

public class CookieCardBiteAction extends AbstractGameAction {
    CardCookie target;

    public CookieCardBiteAction(CardCookie target) {
        this.target = target;
    }

    public void update() {
        isDone = true;
        if (target.upgraded) {
            return;
        }
        int bites = CardCookie.CookieBiteField.bites.get(target);
        if (FleetingField.fleeting.get(target)) {
            target.onEaten();
        }
        target.setBites(bites + 1);
        AbstractCard masterCard = StSLib.getMasterDeckEquivalent(target);
        if (masterCard instanceof CardCookie) {
            CardCookie masterCookie = (CardCookie) masterCard;
            if (
                    !masterCard.upgraded && // The master card might be upgraded and the local copy unupgraded, from effects like Lesson Learned.
                    bites + 1 > CardCookie.CookieBiteField.bites.get(masterCookie) // Playing a cookie shouldn't make the master copy's bite count decrease.
            ) {
                masterCookie.setBites(bites + 1);
            }
        }
    }
}
