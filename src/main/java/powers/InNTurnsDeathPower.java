package powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class InNTurnsDeathPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:InNTurnsDeath";
    private static final PowerStrings originalPowerStrings = CardCrawlGame.languagePack.getPowerStrings(EndTurnDeathPower.POWER_ID);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public InNTurnsDeathPower(AbstractCreature owner, int amount) {
        name = originalPowerStrings.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("end_turn_death");
    }

    public void updateDescription() {
        if (amount < 2) {
            description = originalPowerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
    }

    public void atStartOfTurn() {
        if (amount > 1) {
            amount--;
            if (amount == 1) {
                amount = -1;
            }
            updateDescription();
            return;
        }
        flash();
        addToBot(new VFXAction(new LightningEffect(owner.hb.cX, owner.hb.cY)));
        addToBot(new LoseHPAction(owner, owner, 99999));
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }
}
