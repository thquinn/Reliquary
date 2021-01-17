package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPorcupineQuills extends CustomRelic {
    public static final String ID = "reliquary:PorcupineQuills";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/porcupineQuills.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/outlines/porcupineQuills.png");

    public RelicPorcupineQuills() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        int unblocked = damageAmount - AbstractDungeon.player.currentBlock;
        if (unblocked >= 12) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DamageAction(
                    info.owner,
                    new DamageInfo(AbstractDungeon.player, unblocked, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL
            ));
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPorcupineQuills();
    }
}
