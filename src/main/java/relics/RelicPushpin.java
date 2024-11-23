package relics;

import actions.AddCardModToHandAction;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import cardmods.CardModRetain;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPushpin extends ReliquaryRelic {
    public static final String ID = "reliquary:Pushpin";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/pushpin.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/pushpin.png");

    private boolean activated = false;

    public RelicPushpin() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK, RelicType.GREEN);
    }

    public void atBattleStartPreDraw() {
        activated = false;
    }

    public void atTurnStartPostDraw() {
        if (!activated) {
            activated = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new AddCardModToHandAction(new CardModRetain()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPushpin();
    }
}
