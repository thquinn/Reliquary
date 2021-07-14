package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import util.TextureLoader;

public class LoseFocusPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:FocusDown";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/focusDown32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/focusDown84.png");

    public LoseFocusPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        type = PowerType.DEBUFF;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        this.amount = amount;
        updateDescription();
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, -amount)));
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }
}
