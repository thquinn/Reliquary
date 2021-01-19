package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicRoseTintedGlasses extends CustomRelic {
    public static final String ID = "reliquary:RoseTintedGlasses";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/roseTintedGlasses.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/roseTintedGlasses.png");

    public RelicRoseTintedGlasses() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = 2;
    }

    @Override
    public void atBattleStart() {
        counter = 2;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (counter > 0 && drawnCard.type == AbstractCard.CardType.STATUS) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ExhaustSpecificCardAction(drawnCard, AbstractDungeon.player.hand));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            counter--;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRoseTintedGlasses();
    }
}
