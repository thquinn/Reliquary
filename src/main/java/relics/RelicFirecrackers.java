package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicFirecrackers extends CustomRelic {
    public static final String ID = "reliquary:Firecrackers";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/firecrackers.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/firecrackers.png");

    public RelicFirecrackers() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().monsters.monsters.size() <= 1)
            return;
        int overkill = damageAmount - target.currentHealth;
        if (overkill <= 0)
            return;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DamageAllButOneEnemyAction(
                AbstractDungeon.player,
                target,
                DamageInfo.createDamageMatrix(overkill, true),
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.FIRE,
                true
        ));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicFirecrackers();
    }
}
