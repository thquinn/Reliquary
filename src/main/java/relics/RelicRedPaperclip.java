package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;
import util.TextureLoader;

public class RelicRedPaperclip extends CustomRelic {
    public static final String ID = "reliquary:RedPaperclip";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/redPaperclip.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/redPaperclip.png");

    AbstractRelic toRemove;

    public RelicRedPaperclip() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.tier == RelicTier.COMMON) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onEquip() {
        AbstractRelic[] commons = AbstractDungeon.player.relics.stream().filter(r -> r.tier == RelicTier.COMMON).toArray(AbstractRelic[]::new);
        if (commons.length > 0) {
            toRemove = commons[MathUtils.random(commons.length - 1)];
            ReliquaryLogger.log("set toRemove: " + toRemove.relicId);
        }
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public void postUpdate() {
        // Losing relics during onEquip causes the relics list to get changed during a foreach...?
        if (toRemove != null) {
            ReliquaryLogger.log("trying to remove.");
            AbstractDungeon.player.loseRelic(toRemove.relicId);
            toRemove = null;
        }
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRedPaperclip();
    }
}
