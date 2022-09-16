package relics;

import basemod.abstracts.CustomRelic;
import cards.colorless.CardPearlescence;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicTwinPearls extends CustomRelic {
    public static final String ID = "reliquary:TwinPearls";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/twinPearls.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/twinPearls.png");

    boolean firstTurn;

    public RelicTwinPearls() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        // Cards aren't centered properly with this call for some reason.
        addToBot(new MakeTempCardInHandAction(new CardPearlescence()));
        firstTurn = true;
    }
    @Override
    public void atTurnStart() {
        if (firstTurn) {
            firstTurn = false;
            return;
        }
        addToBot(new MakeTempCardInHandAction(new CardPearlescence()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTwinPearls();
    }
}
