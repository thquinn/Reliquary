package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class TauntPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:Taunt";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/taunt32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/taunt84.png");

    public TauntPower(AbstractCreature owner) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        updateDescription();
    }

    public static AbstractMonster getTaunter() {
        return AbstractDungeon.getMonsters().monsters.stream().filter(mo -> !mo.halfDead && !mo.escaped && mo.hasPower(TauntPower.POWER_ID)).findAny().orElse(null);
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];
    }
}
