package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicKillPill extends CustomRelic {
    public static final String ID = "reliquary:KillPill";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/killPill.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/killPill.png");

    public RelicKillPill() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    // implemented in PatchKillPill

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicKillPill();
    }
}
