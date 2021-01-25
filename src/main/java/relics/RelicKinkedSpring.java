package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
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
