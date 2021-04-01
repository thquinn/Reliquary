package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import cardmods.CardModIncreaseCost;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicCraggleroot extends CustomRelic {
    public static final String ID = "reliquary:Craggleroot";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/craggleroot.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/craggleroot.png");

    boolean activated = false;

    public RelicCraggleroot() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void atTurnStart() {
        activated = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!activated && drawnCard.cost != -1) {
            CardModifierManager.addModifier(drawnCard, new CardModIncreaseCost(1));
            drawnCard.flash(Color.RED);
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            activated = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicCraggleroot();
    }
}
