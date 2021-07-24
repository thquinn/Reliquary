package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicMudwinsCradle extends CustomRelic {
    public static final String ID = "reliquary:MudwinsCradle";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/mudwinsCradle.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/mudwinsCradle.png");

    public static int REDUCTION = 3;

    public RelicMudwinsCradle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + REDUCTION + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicMudwinsCradle();
    }
}
