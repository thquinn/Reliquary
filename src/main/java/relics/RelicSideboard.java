package relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import ui.SideboardButton;
import util.TextureLoader;

import java.util.List;
import java.util.stream.Collectors;

// thanks to Github user ReinaSHSL for much of this
public class RelicSideboard extends CustomRelic implements CustomSavable<List<CardSave>>, ClickableRelic {
    public static final String ID = "reliquary:Sideboard";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/sideboard.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/sideboard.png");

    public static SideboardButton sideboardButton = new SideboardButton();
    public static int COUNTER_ENABLED = -1;
    public static int COUNTER_DISABLED = -2;

    public CardGroup cards;
    boolean cardSelected = true;

    public RelicSideboard() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
        cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    @Override
    public void onEquip() {
        BaseMod.addTopPanelItem(sideboardButton);
        counter = COUNTER_ENABLED;
    }

    public boolean isEnabled() {
        return counter == COUNTER_ENABLED;
    }

    @Override
    public void onRightClick() {
        counter = isEnabled() ? COUNTER_DISABLED : COUNTER_ENABLED;
        grayscale = counter == COUNTER_DISABLED;
        setDescriptionAfterLoading();
    }

    public void onClickTopPanelItem() {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        if (!cardSelected) {
            return; // The sideboard is already open.
        }
        cardSelected = false;
        if (cards.isEmpty()) {
            cardSelected = true;
        } else {
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(cards, 1, DESCRIPTIONS[5], false, false, true, false);
            AbstractDungeon.overlayMenu.cancelButton.show("Cancel");
        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            cards.removeCard(card);
            AbstractDungeon.effectsQueue.add(new FastCardObtainEffect(card, card.current_x, card.current_y));
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (!cardSelected && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
            // Canceled.
            cardSelected = true;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public void onUnequip() {
        BaseMod.removeTopPanelItem(sideboardButton);
    }

    @Override
    public List<CardSave> onSave() {
        return cards.group.stream().map(c -> new CardSave(c.cardID, c.timesUpgraded, c.misc)).collect(Collectors.toList());
    }

    @Override
    public void onLoad(List<CardSave> cardSaves) {
        BaseMod.removeTopPanelItem(sideboardButton);
        BaseMod.addTopPanelItem(sideboardButton);
        for (CardSave s : cardSaves) {
            cards.addToTop(CardLibrary.getCopy(s.id, s.upgrades, s.misc));
        }
        grayscale = counter == COUNTER_DISABLED;
        setDescriptionAfterLoading();
    }

    public void setDescriptionAfterLoading() {
        description = DESCRIPTIONS[0] + (isEnabled() ? DESCRIPTIONS[3] : DESCRIPTIONS[4]);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSideboard();
    }
}
