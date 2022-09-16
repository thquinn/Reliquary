package relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import cardmods.CardModBouncer;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import patches.PatchBouncer;
import util.TextureLoader;

import java.util.function.Predicate;

public class RelicBouncer extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {
    public static final String ID = "reliquary:Bouncer";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bouncer.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bouncer.png");

    private static AbstractCard card;
    private boolean cardSelected = true;

    public RelicBouncer() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public boolean canSpawn() {
        return !CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).getAttacks().isEmpty();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return PatchBouncer.inBouncer::get;
    }
    @Override
    public Integer onSave() {
        if (card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(card);
        } else {
            return -1;
        }
    }
    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                PatchBouncer.inBouncer.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public void onEquip() { // 1. When we acquire the relic
        cardSelected = false;
        CardGroup group = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck);
        if (group.isEmpty()) {
            cardSelected = true;
        } else {
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[1] + name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    @Override
    public void onUnequip() { // 1. On unequip
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                PatchBouncer.inBouncer.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update(); //Do all of the original update() method in AbstractRelic

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            PatchBouncer.inBouncer.set(card, true);
            CardModifierManager.addModifier(card, new CardModBouncer());
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    public void setDescriptionAfterLoading() {
        description = DESCRIPTIONS[2] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[3];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        tips.subList(1, tips.size()).clear(); // remove keyword tips from words in the card's name
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

