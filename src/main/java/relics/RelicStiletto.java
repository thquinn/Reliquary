package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicStiletto extends CustomRelic {
    public static final String ID = "reliquary:Stiletto";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/stiletto.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/stiletto.png");

    public RelicStiletto() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // implemented in PatchStiletto

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicStiletto();
    }
}
