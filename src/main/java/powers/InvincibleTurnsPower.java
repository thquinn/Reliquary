package powers;


import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InvincibleTurnsPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:InvincibleTurns";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public InvincibleTurnsPower(AbstractCreature owner, int turns) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        this.owner = owner;
        amount = turns;
        loadRegion("heartDef");
        priority = 99;
        updateDescription();
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            flash();
        }
        return 0;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        amount--;
        if (amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        } else {
            updateDescription();
        }
    }

    public void updateDescription() {
        description = amount == 1 ? powerStrings.DESCRIPTIONS[0] : powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
    }
}
