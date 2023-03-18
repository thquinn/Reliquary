package relics;

import actions.ApplyPowerIfAbsentAction;
import actions.DamageRandomEnemyExceptAction;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.relic.Relic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import powers.IntangibleTurnsPower;
import util.TextureLoader;

public class RelicFreeSamples extends CustomRelic implements BetterOnLoseHpRelic {
    public static final String ID = "reliquary:FreeSamples";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/freeSamples.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/freeSamples.png");

    public RelicFreeSamples() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
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
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner != p && !info.owner.hasPower(IntangibleTurnsPower.POWER_ID)) {
            addToBot(new ApplyPowerIfAbsentAction(info.owner, p, new IntangibleTurnsPower(info.owner, 1), this));
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
        return new RelicFreeSamples();
    }
}
