package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPrincelyHelmet extends CustomRelic {
    public static final String ID = "reliquary:PrincelyHelmet";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/princelyHelmet.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/princelyHelmet.png");

    public RelicPrincelyHelmet() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = AbstractDungeon.floorNum;
    }

    public void atBattleStart() {
        flash();
        counter = AbstractDungeon.floorNum;
        grayscale = false;
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        if (counter > 0) {
            int additional = Math.min(counter, (int)Math.floor(blockAmount));
            counter -= additional;
            blockAmount += additional;
            flash();
            if (counter <= 0) {
                grayscale = true;
            }
        }
        return super.onPlayerGainedBlock(blockAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPrincelyHelmet();
    }
}
