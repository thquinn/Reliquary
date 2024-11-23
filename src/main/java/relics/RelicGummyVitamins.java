package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import util.StaticHelpers;
import util.TextureLoader;

public class RelicGummyVitamins extends ReliquaryRelic implements ClickableRelic {
    public static final String ID = "reliquary:GummyVitamins";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/gummyVitamins.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/gummyVitamins.png");

    private boolean usedThisTurn = false;
    public AbstractGameAction dummyAction;

    public RelicGummyVitamins() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        usedThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void onRightClick() {
        if (!StaticHelpers.canClickRelic(this)) {
            return;
        }
        if (usedThisTurn) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, DESCRIPTIONS[1], true));
            return;
        }
        // We use a dummy follow-up action to track the completion of the draw action, even if a shuffle happens and a
        // new one is created.
        dummyAction = new WaitAction(0);
        addToBot(new DrawCardAction(1, dummyAction));
    }

    public void drawSuccessFollowUp() {
        addToBot(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, true));
        dummyAction = null;
        usedThisTurn = true;
        stopPulse();
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
