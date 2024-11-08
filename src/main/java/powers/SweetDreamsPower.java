package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class SweetDreamsPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:SweetDreamsPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/sweetDreams32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/sweetDreams84.png");

    public SweetDreamsPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new DrawCardAction(amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
}
