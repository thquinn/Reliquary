package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicLoveEmittingDiode extends CustomRelic {
    public static final String ID = "reliquary:LoveEmittingDiode";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/loveEmittingDiode.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/loveEmittingDiode.png");

    public RelicLoveEmittingDiode() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    // implemented in PatchLoveEmittingDiode

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicLoveEmittingDiode();
    }
}