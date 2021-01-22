package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicTuningFork extends CustomRelic {
    public static final String ID = "reliquary:TuningFork";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/tuningFork.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/tuningFork.png");

    public RelicTuningFork() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void onAttackBeforeBlock(AbstractMonster target, int damageAmount) {
        if (damageAmount > 0 && damageAmount == target.currentBlock) {
            addToBot(new RelicAboveCreatureAction(target, this));
            addToBot(new DamageAction(
                    target,
                    new DamageInfo(AbstractDungeon.player, 10, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY
            ));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTuningFork();
    }
}
