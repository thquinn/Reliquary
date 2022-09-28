package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import util.TextureLoader;

public class IntangibleTurnsPower extends IntangiblePower {
    // uses the power ID from base-game Intangible
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("reliquary:IntangibleTurns");

    public IntangibleTurnsPower(AbstractCreature owner, int turns) {
        super(owner, turns);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }
}
