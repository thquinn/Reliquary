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

    static int MULTIPLIER = 8;

    public RelicPrincelyHelmet() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        atBattleStart();
    }

    public void atBattleStart() {
        flash();
        counter = AbstractDungeon.actNum * MULTIPLIER;
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
        return DESCRIPTIONS[0] + MULTIPLIER + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPrincelyHelmet();
    }
}
