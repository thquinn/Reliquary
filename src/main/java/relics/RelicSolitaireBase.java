package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import basemod.helpers.RelicType;
import cardmods.CardModSolitairized;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import util.ReliquaryLogger;

import java.lang.reflect.Method;

public abstract class RelicSolitaireBase extends ReliquaryRelic {
    private boolean cardsSelected = true;

    public RelicSolitaireBase(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, RelicType relicType) {
        super(id, texture, outline, tier, sfx, relicType);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.masterDeck.group.stream().anyMatch(c -> c.color != AbstractCard.CardColor.COLORLESS && c.upgraded);
    }

    @Override
    public void onEquip() {
        cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.upgraded && card.canUpgrade()) {
                tmp.addToTop(card);
            }
        }
        if (tmp.isEmpty()) {
            cardsSelected = true;
            return;
        }
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        String upgradeText = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect").TEXT[0];
        AbstractDungeon.gridSelectScreen.open(tmp, 1, upgradeText, true, false, false, false);
    }

    public void upgradeCard(AbstractCard card) {
        flash();
        try {
            Method upgradeName = AbstractCard.class.getDeclaredMethod("upgradeName");
            upgradeName.setAccessible(true);
            upgradeName.invoke(card);
        } catch (Exception e) {
            ReliquaryLogger.error( relicId + " failed to call upgradeName().");
        }
        card.initializeDescription();
        CardModifierManager.addModifier(card, new CardModSolitairized());
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() > 0) {
            cardsSelected = true;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            c.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    public static void changeBaseCost(AbstractCard card, int newBaseCost) {
        int diff = card.costForTurn - card.cost;
        card.cost = newBaseCost;
        if (card.costForTurn > 0)
            card.costForTurn = card.cost + diff;
        if (card.costForTurn < 0)
            card.costForTurn = 0;
        card.upgradedCost = true;
    }
}
