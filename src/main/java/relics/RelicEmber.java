package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnAfterUseCardRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicEmber extends CustomRelic implements OnAfterUseCardRelic {
    public static final String ID = "reliquary:Ember";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ember.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ember.png");

    static int DAMAGE  = 2;

    public RelicEmber() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
        counter = 0;
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
        counter = 0;
    }

    @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction useCardAction) {
        if (c.cost == -2) {
            // Unplayable cards.
            return;
        }
        if (c.freeToPlay()) {
            return;
        }
        if (c.isInAutoplay) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        int cost = c.cost == -1 ? c.energyOnUse : c.costForTurn;
        counter += cost;
        int damage = Math.min(counter - 3, cost) * DAMAGE;
        if (damage <= 0) {
            return;
        }
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new DamageAction(p, new DamageInfo(null, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicEmber();
    }
}
