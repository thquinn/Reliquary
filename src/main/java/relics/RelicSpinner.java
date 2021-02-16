package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicSpinner extends CustomRelic {
    public static final String ID = "reliquary:Spinner";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/spinner.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/spinner.png");

    static int BLOCK_AMOUNT = 1;

    boolean cardPlayedThisTurn;
    AbstractCard.CardType previousType;

    public RelicSpinner() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        cardPlayedThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        AbstractPlayer p = AbstractDungeon.player;
        if (cardPlayedThisTurn && c.type != previousType) {
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new GainBlockAction(p, p, BLOCK_AMOUNT));
        }
        cardPlayedThisTurn = true;
        previousType = c.type;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_AMOUNT + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSpinner();
    }
}
