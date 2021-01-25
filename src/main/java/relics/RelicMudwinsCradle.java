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
import util.TextureLoader;

import java.awt.dnd.DragSourceEvent;

public class RelicMudwinsCradle extends CustomRelic {
    public static final String ID = "reliquary:MudwinsCradle";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/mudwinsCradle.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/mudwinsCradle.png");

    static int BOOST = 2;
    static int MAX = 9;

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
            if (counter < MAX) {
                flash();
            }
            counter = Math.min(counter + BOOST, MAX);
        } else if (prevStance.ID.equals(DivinityStance.STANCE_ID)) {
            int toGain = counter;
            AbstractPower mantraPower = AbstractDungeon.player.getPower(MantraPower.POWER_ID);
            if (mantraPower != null && mantraPower.amount + toGain > MAX) {
                // Don't bring mantra back to 10 immediately.
                toGain -= mantraPower.amount + toGain - MAX;
            }
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, toGain)));
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BOOST + DESCRIPTIONS[1] + MAX + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicMudwinsCradle();
    }
}
