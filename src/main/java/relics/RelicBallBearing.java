package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.colorless.Finesse;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicBallBearing extends CustomRelic {
    public static final String ID = "reliquary:BallBearing";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ballBearing.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ballBearing.png");

    public RelicBallBearing() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onShuffle() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInDrawPileAction(MathUtils.randomBoolean() ? new Finesse() : new FlashOfSteel(), 1, true, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBallBearing();
    }
}
