package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicTatteredRug extends CustomRelic {
    public static final String ID = "reliquary:TatteredRug";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/tatteredRug.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/outlines/tatteredRug.png");

    public RelicTatteredRug() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // implemented in PatchTatteredRug

    public static int GetPrice(AbstractPotion potion) {
        if (potion.rarity == AbstractPotion.PotionRarity.RARE)
            return 90;
        if (potion.rarity == AbstractPotion.PotionRarity.UNCOMMON)
            return 70;
        return 45;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTatteredRug();
    }
}
