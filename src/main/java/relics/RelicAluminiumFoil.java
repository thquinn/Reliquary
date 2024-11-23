package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicAluminiumFoil extends ReliquaryRelic {
    public static final String ID = "reliquary:AluminiumFoil";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/aluminiumFoil.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/aluminiumFoil.png");

    public static int RARITY_STRENGTH = 1;
    public static float UPGRADE_STRENGTH = .25f;

    public RelicAluminiumFoil() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicAluminiumFoil();
    }
}
