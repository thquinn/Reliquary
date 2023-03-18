package relics;

import basemod.abstracts.CustomRelic;
import cards.colorless.CardPearlescence;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;
import util.TextureLoader;

import javax.smartcardio.Card;

public class RelicTwinPearls extends CustomRelic {
    public static final String ID = "reliquary:TwinPearls";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/twinPearls.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/twinPearls.png");

    boolean firstTurn;

    public RelicTwinPearls() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public boolean canSpawn() {
        return !AbstractDungeon.player.chosenClass.toString().equals("THE_AUTOMATON");
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
        if (AbstractDungeon.player.hand.group.stream().noneMatch(c -> c instanceof CardPearlescence)) {
            addToBot(new MakeTempCardInHandAction(new CardPearlescence()));
        }
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
