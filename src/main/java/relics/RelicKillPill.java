package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.HashSet;
import java.util.Set;

public class RelicKillPill extends CustomRelic {
    public static final String ID = "reliquary:KillPill";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/killPill.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/killPill.png");

    Set<AbstractCreature> triggeredThisTurn;

    public RelicKillPill() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
        triggeredThisTurn = new HashSet<>();
    }

    @Override
    public void atTurnStart() {
        triggeredThisTurn.clear();
    }

    // trigger implemented in PatchKillPill
    public boolean shouldTrigger(AbstractCreature creature) {
        boolean ret = !triggeredThisTurn.contains(creature);
        triggeredThisTurn.add(creature);
        return ret;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicKillPill();
    }
}
