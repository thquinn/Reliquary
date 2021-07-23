package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPrayerBox extends CustomRelic {
    public static final String ID = "reliquary:PrayerBox";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/prayerBox.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/prayerBox.png");

    public RelicPrayerBox() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void atBattleStart() {
        if (counter > 0) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new ApplyPowerAction(p, p, new MantraPower(p, counter)));
            counter = 0;
        }
    }

    @Override
    public void onVictory() {
        AbstractPower mantra = AbstractDungeon.player.getPower(MantraPower.POWER_ID);
        if (mantra != null && mantra.amount > 0) {
            flash();
            counter = mantra.amount;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPrayerBox();
    }
}
