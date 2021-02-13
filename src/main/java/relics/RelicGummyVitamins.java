package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicGummyVitamins extends CustomRelic implements ClickableRelic {
    public static final String ID = "reliquary:GummyVitamins";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/gummyVitamins.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/gummyVitamins.png");

    public DrawCardAction drawCardAction;

    public RelicGummyVitamins() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onRightClick() {
        drawCardAction = new DrawCardAction(1);
        addToBot(drawCardAction);
    }

    public void drawSuccessFollowUp() {
        addToTop(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, true));
        drawCardAction = null;
    }

    @Override
    public String getUpdatedDescription() {
        return CLICKABLE_DESCRIPTIONS()[0] + " NL " + DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicGummyVitamins();
    }
}
