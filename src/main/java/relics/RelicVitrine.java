package relics;

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
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import util.TextureLoader;
import vfx.ShowRealCardAndAddToDrawPileEffect;

public class RelicVitrine extends CustomRelic implements CustomSavable<CardSave>, ClickableRelic {
    public static final String ID = "reliquary:Vitrine";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/vitrine.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/vitrine.png");

    public AbstractCard card = null;

    public RelicVitrine() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public boolean canSpawn() {
        return !AbstractDungeon.player.masterDeck.getPurgeableCards().isEmpty();
    }

    @Override
    public void onEquip() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : (AbstractDungeon.player.masterDeck.getPurgeableCards()).group)
            tmp.addToTop(card);
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, this.DESCRIPTIONS[1], false, false, false, true);
    }

    @Override
    public void update() {
        super.update();
        if (card == null && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    @Override
    public void onVictory() {
        grayscale = false;
        setDescriptionAfterLoading();
    }

    @Override
    public void onRightClick() {
        if (IsInappropriate()) {
            return;
        }
        if (!grayscale) {
            AbstractDungeon.effectList.add(new ShowRealCardAndAddToDrawPileEffect(card.makeSameInstanceOf(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true, true, false));
            grayscale = true;
            setDescriptionAfterLoading();
        }
    }
    private boolean IsInappropriate() {
        return !isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.actionManager.turnHasEnded || !AbstractDungeon.actionManager.actions.isEmpty();
    }

    @Override
    public CardSave onSave() {
        return new CardSave(card.cardID, card.timesUpgraded, card.misc);
    }

    @Override
    public void onLoad(CardSave cardSave) {
        card = CardLibrary.getCopy(cardSave.id, cardSave.upgrades, cardSave.misc);
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + " NL " + CLICKABLE_DESCRIPTIONS()[1];
    }
    public void setDescriptionAfterLoading() {
        if (grayscale) {
            description = FontHelper.colorString(card.name, "y") + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[2] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[3];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicVitrine();
    }
}
