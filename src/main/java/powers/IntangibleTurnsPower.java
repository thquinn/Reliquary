package powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class IntangibleTurnsPower extends IntangiblePlayerPower {
    // uses the power ID from base-game IntangiblePlayerPower
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("reliquary:IntangibleTurns");

    boolean justApplied;

    public IntangibleTurnsPower(AbstractCreature owner, int turns) {
        super(owner, turns);
        justApplied = true;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    @Override
    public void atEndOfRound() {
        if (justApplied) {
            justApplied = false;
            return;
        }
        super.atEndOfRound();
    }
}
