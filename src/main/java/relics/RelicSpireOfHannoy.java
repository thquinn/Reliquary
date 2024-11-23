package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicSpireOfHannoy extends ReliquaryRelic {
    public static final String ID = "reliquary:SpireOfHannoy";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/spireOfHannoy.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/spireOfHannoy.png");

    public static float POWER_PER_STACK = .1f;
    public static float MAX_POWER = .3f;

    public RelicSpireOfHannoy() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + Math.round(POWER_PER_STACK * 100) + DESCRIPTIONS[1] + Math.round(MAX_POWER * 100) + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSpireOfHannoy();
    }
}
