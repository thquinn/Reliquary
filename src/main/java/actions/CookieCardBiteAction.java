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
        AbstractCard masterCard = StSLib.getMasterDeckEquivalent(target);
        int bites = CardCookie.CookieBiteField.bites.get(target);
        target.setBites(bites + 1);
        if (masterCard instanceof CardCookie) {
            ((CardCookie) masterCard).setBites(bites + 1);
        }
    }
}
