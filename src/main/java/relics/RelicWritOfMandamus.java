package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicWritOfMandamus extends CustomRelic {
    public static final String ID = "reliquary:WritOfMandamus";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/writOfMandamus.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/writOfMandamus.png");

    private static final int BLOCK = 3;

    public RelicWritOfMandamus() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
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
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.currentBlock > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new LoseBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicWritOfMandamus();
    }
}
