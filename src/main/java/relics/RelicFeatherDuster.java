package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import util.StaticHelpers;
import util.TextureLoader;

public class RelicFeatherDuster extends CustomRelic {
    public static final String ID = "reliquary:FeatherDuster";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/featherDuster.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/featherDuster.png");

    private boolean cardsSelected = true;
    private AbstractDungeon.CurrentScreen prevScreen;

    public RelicFeatherDuster() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        StaticHelpers.removeStrikeTips(this);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.floorNum > 20 && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }

    @Override
    public void onEquip() {
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            if (!card.hasTag(AbstractCard.CardTags.STARTER_DEFEND) && !card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && card.type != AbstractCard.CardType.CURSE)
                tmp.addToTop(card);
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
            return;
        }
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        prevScreen = AbstractDungeon.screen;
        AbstractDungeon.gridSelectScreen.open(tmp, tmp.size(), true, this.DESCRIPTIONS[1]);
    }

    @Override
    public void update() {
        super.update();
        if (!cardsSelected && AbstractDungeon.screen == prevScreen) {
            cardsSelected = true;
            int numCards = AbstractDungeon.gridSelectScreen.selectedCards.size();
            float sigmoid = 1 / (1 + (float)Math.pow(Math.E, -numCards * .4)) - .5f;
            sigmoid *= .8f;
            for (int i = 0; i < numCards; i++) {
                float xPercent = numCards == 1 ? .5f : MathUtils.lerp(.5f - sigmoid, .5f + sigmoid, (float)i / (numCards - 1));
                AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(i);
                card.untip();
                card.unhover();
                card.stopGlowing();
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, 0, 0));
                card.current_x = Settings.WIDTH / 2f;
                card.current_y = 0;
                card.target_x = Settings.WIDTH * xPercent;
                card.target_y = Settings.HEIGHT / 2f;
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicFeatherDuster();
    }
}
