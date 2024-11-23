package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.StaticHelpers;
import util.TextureLoader;

public class RelicSheepsEyeMarble extends ReliquaryRelic {
    public static final String ID = "reliquary:SheepsEyeMarble";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/sheepsEyeMarble.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/sheepsEyeMarble.png");

    boolean triggeredThisTurn;
    String lastID;

    public RelicSheepsEyeMarble() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        lastID = null;
    }
    @Override
    public void atTurnStart() {
        triggeredThisTurn = false;
    }
    @Override
    public void onPlayerEndTurn() {
        lastID = null;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (triggeredThisTurn) {
            return;
        }
        if (c.cardID.equals(lastID) && !c.purgeOnUse) {
            flash();
            StaticHelpers.duplicatePlayedCard(c, m);
            triggeredThisTurn = true;
        } else {
            lastID = c.cardID;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSheepsEyeMarble();
    }
}
