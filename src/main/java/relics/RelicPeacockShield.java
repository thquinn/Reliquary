package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DoubleYourBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import util.TextureLoader;

public class RelicPeacockShield extends ReliquaryRelic {
    public static final String ID = "reliquary:PeacockShield";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/peacockShield.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/peacockShield.png");

    public static int BLOCK_GAIN = 2;

    public RelicPeacockShield() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL, RelicType.PURPLE);
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (newStance.ID.equals(WrathStance.STANCE_ID)) {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_GAIN));
            addToBot(new DoubleYourBlockAction(AbstractDungeon.player));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_GAIN + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPeacockShield();
    }
}
