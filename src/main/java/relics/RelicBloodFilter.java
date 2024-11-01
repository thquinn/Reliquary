package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

public class RelicBloodFilter extends CustomRelic {
    public static final String ID = "reliquary:BloodFilter";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bloodFilter.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bloodFilter.png");

    public RelicBloodFilter() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.actionManager.turnHasEnded) {
            counter += damageAmount;
            flash();
        }
    }

    @Override
    public void onVictory() {
        if (counter > 0) {
            AbstractDungeon.player.heal(counter);
            flash();
        }
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBloodFilter();
    }
}
