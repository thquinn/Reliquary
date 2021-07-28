package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import powers.InNTurnsDeathPower;
import util.TextureLoader;

public class RelicHolyGrail extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = "reliquary:HolyGrail";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/holyGrail.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/holyGrail.png");

    boolean triggeredThisCombat;

    public RelicHolyGrail() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        triggeredThisCombat = false;
        grayscale = false;
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer p, DamageInfo damageInfo) {
        if (triggeredThisCombat) {
            return true;
        }
        triggeredThisCombat = true;
        grayscale = true;
        if (AbstractDungeon.actionManager.turnHasEnded) {
            addToTop(new ApplyPowerAction(p, p, new InNTurnsDeathPower(p, 2)));
        } else {
            addToTop(new ApplyPowerAction(p, p, new EndTurnDeathPower(p)));
        }
        addToTop(new RelicAboveCreatureAction(p, this));
        p.heal(1);
        return false;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicHolyGrail();
    }
}
