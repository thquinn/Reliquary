package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPartyBalloon extends CustomRelic {
    public static final String ID = "reliquary:PartyBalloon";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/partyBalloon.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/partyBalloon.png");

    public RelicPartyBalloon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    // implemented in PatchPartyBalloon

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPartyBalloon();
    }
}
