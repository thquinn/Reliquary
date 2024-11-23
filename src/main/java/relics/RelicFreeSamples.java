package relics;

import actions.ApplyPowerIfAbsentAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import powers.IntangibleTurnsPower;
import util.TextureLoader;

public class RelicFreeSamples extends ReliquaryRelic implements BetterOnLoseHpRelic {
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
            addToBot(new ApplyPowerIfAbsentAction(info.owner, p, new BufferPower(info.owner, 1), this));
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
