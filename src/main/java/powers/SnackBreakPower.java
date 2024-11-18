package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class SnackBreakPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:SnackBreakPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/snackBreak32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/snackBreak84.png");

    public SnackBreakPower(AbstractCreature owner) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        type = PowerType.BUFF;
        updateDescription();
    }

    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToBot(new GainBlockAction(owner, owner, damageAmount, true));
        }
        return 0;
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];
    }
}
