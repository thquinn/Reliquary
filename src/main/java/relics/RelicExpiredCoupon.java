package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import util.TextureLoader;

public class RelicExpiredCoupon extends CustomRelic {
    public static final String ID = "reliquary:ExpiredCoupon";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/expiredCoupon.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/expiredCoupon.png");

    private static final int THRESHOLD = 70;
    private static final int DAMAGE = 10;

    public RelicExpiredCoupon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void atBattleStart() {
        counter = 0;
        grayscale = false;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (counter == -1) {
            return;
        }
        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) {
            return;
        }
        counter += damageAmount;
        if (counter >= THRESHOLD) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DamageAllEnemiesAction(
                    AbstractDungeon.player,
                    DamageInfo.createDamageMatrix(DAMAGE, true),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                    true
            ));
            counter = -1;
            grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] + DAMAGE + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicExpiredCoupon();
    }
}
