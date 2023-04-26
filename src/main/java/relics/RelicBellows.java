package relics;

import actions.BellowsAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicBellows extends CustomRelic {
    public static final String ID = "reliquary:Bellows";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bellows.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bellows.png");

    static final int N = 5;

    public RelicBellows() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        counter++;
        if (counter >= N) {
            addToBot(new BellowsAction(this));
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            counter -= N;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + N + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBellows();
    }
}
