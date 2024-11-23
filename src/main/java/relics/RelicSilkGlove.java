package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicSilkGlove extends ReliquaryRelic {
    public static final String ID = "reliquary:SilkGlove";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/silkGlove.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/silkGlove.png");

    public RelicSilkGlove() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // implemented in PatchSilkGlove
    // done as a patch so we can do our check after Well-Laid Plans has already resolved

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSilkGlove();
    }
}
