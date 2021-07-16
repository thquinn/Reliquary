package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public abstract class RelicSolitaireBase extends CustomRelic {
    private boolean cardsSelected = true;

    public RelicSolitaireBase(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
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
