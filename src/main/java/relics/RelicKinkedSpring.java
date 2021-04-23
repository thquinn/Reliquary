package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicKinkedSpring extends CustomRelic {
    public static final String ID = "reliquary:KinkedSpring";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/kinkedSpring.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/kinkedSpring.png");

    public RelicKinkedSpring() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public boolean canSpawn() {
        int mod5 = AbstractDungeon.player.masterDeck.size() % 5;
        if (mod5 > 0 && mod5 < 3 && AbstractDungeon.floorNum == 0) {
            // Neow boss relic swapping into Kinked Spring is a death sentence with an off-sized deck.
            return false;
        }
        return true;
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

    // downside implemented in PatchKinkedSpring

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicKinkedSpring();
    }
}
