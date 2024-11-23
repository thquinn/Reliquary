package relics;

import basemod.abstracts.CustomRelic;
import cards.colorless.CardPearlescence;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicTwinPearls extends ReliquaryRelic {
    public static final String ID = "reliquary:TwinPearls";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/twinPearls.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/twinPearls.png");

    boolean firstTurn;

    public RelicTwinPearls() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        counter = 1;
    }

    @Override
    public void atBattleStartPreDraw() {
        // Cards aren't centered properly with this call for some reason.
        flash();
        addToBot(new MakeTempCardInHandAction(new CardPearlescence()));
        firstTurn = true;
    }
    @Override
    public void atTurnStart() {
        if (firstTurn) {
            firstTurn = false;
            return;
        }
        counter++;
        if (counter % 2 == 1 && AbstractDungeon.player.hand.group.stream().noneMatch(c -> c instanceof CardPearlescence)) {
            flash();
            addToBot(new MakeTempCardInHandAction(new CardPearlescence()));
        }
    }

    public void onVictory() {
        this.counter = -1;
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
