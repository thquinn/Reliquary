package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicKnifeBlock extends ReliquaryRelic {
    public static final String ID = "reliquary:KnifeBlock";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/knifeBlock.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/knifeBlock.png");

    public RelicKnifeBlock() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY, RelicType.GREEN);
    }

    // implemented in PatchKnifeBlock

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicKnifeBlock();
    }
}
