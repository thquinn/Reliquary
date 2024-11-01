package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import util.TextureLoader;

public class RelicHardlight extends CustomRelic {
    public static final String ID = "reliquary:Hardlight";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/hardlight.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/hardlight.png");

    private static final int THRESHOLD = 10;
    private static final int STRENGTH_GAIN = 2;

    public RelicHardlight() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained) {
            return;
        }
        if (counter >= STRENGTH_GAIN) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth <= THRESHOLD) {
            counter = STRENGTH_GAIN;
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                flash();
                addToBot(new RelicAboveCreatureAction(p, this));
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_GAIN)));
            }
        }
    }

    @Override
    public void atBattleStart() {
        if (counter > 0) {
            AbstractPlayer p = AbstractDungeon.player;
            flash();
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, counter)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] + STRENGTH_GAIN + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicHardlight();
    }
}
