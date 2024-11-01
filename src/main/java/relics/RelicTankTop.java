package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.List;

public class RelicTankTop extends CustomRelic {
    public static final String ID = "reliquary:TankTop";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/tankTop.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/tankTop.png");

    private static final int DAMAGE = 4;

    public RelicTankTop() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }


    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster target) {
        if (c.type != AbstractCard.CardType.ATTACK || target == null) {
            return;
        }
        List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        for (AbstractMonster m : monsters) {
            if (m.currentHealth > target.currentHealth) {
                return;
            }
        }
        if (monsters.stream().filter(m -> !m.isDeadOrEscaped() && !m.halfDead).count() <= 1) {
            return;
        }
        // Determine the front monster.
        AbstractMonster front = null;
        float frontX = -1;
        for (AbstractMonster m : monsters) {
            if (m.isDeadOrEscaped() || m.halfDead || m.hasPower(BackAttackPower.POWER_ID)) {
                continue;
            }
            float x = m.hb.cX;
            if (front == null || x < frontX) {
                front = m;
                frontX = x;
            }
        }
        // Damage.
        if (front != null) {
            addToBot(new RelicAboveCreatureAction(front, this));
            addToBot(new DamageAction(front, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTankTop();
    }
}
