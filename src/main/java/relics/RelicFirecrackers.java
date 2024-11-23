package relics;

import actions.DamageRandomEnemyExceptAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicFirecrackers extends ReliquaryRelic {
    public static final String ID = "reliquary:Firecrackers";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/firecrackers.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/firecrackers.png");

    public RelicFirecrackers() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target.isPlayer || AbstractDungeon.getMonsters().monsters.size() <= 1)
            return;
        int overkill = damageAmount - target.currentHealth;
        if (overkill <= 0)
            return;
        DamageRandomEnemyExceptAction action = new DamageRandomEnemyExceptAction((AbstractMonster) target, overkill, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE);
        action.relic = this;
        addToBot(action);
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
