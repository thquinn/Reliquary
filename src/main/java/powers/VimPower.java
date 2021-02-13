package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BerserkPower;
import util.TextureLoader;

public class VimPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:Vim";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerStrings berserkStrings = CardCrawlGame.languagePack.getPowerStrings(BerserkPower.POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/vim32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/vim84.png");

    public VimPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    }

    @Override
    public void onEnergyRecharge() {
        flash();
        AbstractDungeon.player.gainEnergy(amount);
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(berserkStrings.DESCRIPTIONS[0]);
        for (int i = 0; i < amount; i++)
            sb.append("[E] ");
        sb.append(LocalizedStrings.PERIOD);
        description = sb.toString();
    }
}
