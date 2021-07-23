package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class AreaDenialPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:AreaDenial";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/areaDenial32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/areaDenial84.png");

    public AreaDenialPower(AbstractCreature owner) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        updateDescription();
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];
    }
}
