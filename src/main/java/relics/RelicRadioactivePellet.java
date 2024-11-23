package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicRadioactivePellet extends ReliquaryRelic {
    public static final String ID = "reliquary:RadioactivePellet";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/radioactivePellet.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/radioactivePellet.png");

    public RelicRadioactivePellet() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK, RelicType.GREEN);
    }

    // implemented in PatchRadioactivePellet

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRadioactivePellet();
    }
}
