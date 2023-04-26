package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

public class RelicFingerTrap extends CustomRelic {
    public static final String ID = "reliquary:FingerTrap";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/fingerTrap.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/fingerTrap.png");

    static int DAMAGE = 13;

    public RelicFingerTrap() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        counter = DAMAGE;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.actionManager.turnHasEnded) {
            return damageAmount;
        }
        if (counter <= 0) {
            return damageAmount;
        }
        int prevent = Math.min(counter, damageAmount);
        damageAmount -= prevent;
        counter -= prevent;
        flash();
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicFingerTrap();
    }
}
