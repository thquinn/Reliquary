package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.sun.nio.sctp.AbstractNotificationHandler;
import sun.management.snmp.jvmmib.JvmMemManagerTableMeta;
import util.TextureLoader;

public class RelicMudwinsCradle extends CustomRelic {
    public static final String ID = "reliquary:MudwinsCradle";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/mudwinsCradle.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/mudwinsCradle.png");

    public RelicMudwinsCradle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (newStance.ID.equals(DivinityStance.STANCE_ID)) {
            if (counter != 9) {
                flash();
            }
            counter = Math.min(counter + 2, 9);
        } else if (prevStance.ID.equals(DivinityStance.STANCE_ID)) {
            int toGain = counter;
            AbstractPower mantraPower = AbstractDungeon.player.getPower(MantraPower.POWER_ID);
            if (mantraPower != null && mantraPower.amount + toGain > 9) {
                // Don't bring mantra back to 10 immediately.
                toGain -= mantraPower.amount + toGain - 9;
            }
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, toGain)));
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicMudwinsCradle();
    }
}
