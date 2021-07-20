package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import orbs.OrbData;
import util.TextureLoader;

public class RelicThumbDrive extends CustomRelic {
    public static final String ID = "reliquary:ThumbDrive";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/thumbDrive.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/thumbDrive.png");

    public RelicThumbDrive() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.channelOrb(new OrbData(true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicThumbDrive();
    }
}
