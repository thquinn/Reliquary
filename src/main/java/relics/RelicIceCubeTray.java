package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import util.TextureLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RelicIceCubeTray extends ReliquaryRelic {
    public static final String ID = "reliquary:IceCubeTray";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/iceCubeTray.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/iceCubeTray.png");
    public static final String[] UI_TEXT = CardCrawlGame.languagePack.getUIString("CampfireTokeEffect").TEXT;

    static int N = 4;
    int lastCount;
    boolean cardsSelected = true;

    public RelicIceCubeTray() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained) {
            return;
        }
        int count = AbstractDungeon.player.masterDeck.size();
        int delta = count - lastCount;
        if (lastCount > 0 && delta > 0) {
            counter += delta;
        }
        lastCount = count;
        AbstractRoom.RoomPhase phase = AbstractDungeon.getCurrRoom().phase;
        if (cardsSelected && counter >= N && phase != AbstractRoom.RoomPhase.INCOMPLETE && phase != AbstractRoom.RoomPhase.COMBAT && VALID_SCREENS.contains(AbstractDungeon.screen)) {
            counter -= N;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, UI_TEXT[0], false, false, true, true);
            AbstractDungeon.dynamicBanner.hide();
            beginLongPulse();
            cardsSelected = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        }
        if (!cardsSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardsSelected = true;
            stopPulse();
            float displayCount = 0;
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                card.untip();
                card.unhover();
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
                displayCount += Settings.WIDTH / 6.0F;
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        } else if (!cardsSelected && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
            // Canceled.
            cardsSelected = true;
            stopPulse();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + N + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicIceCubeTray();
    }

    private final Set<AbstractDungeon.CurrentScreen> VALID_SCREENS = new HashSet<AbstractDungeon.CurrentScreen>(Arrays.asList(
            AbstractDungeon.CurrentScreen.COMBAT_REWARD,
            AbstractDungeon.CurrentScreen.MAP,
            AbstractDungeon.CurrentScreen.NONE,
            AbstractDungeon.CurrentScreen.SHOP,
            AbstractDungeon.CurrentScreen.VICTORY
    ));
}
