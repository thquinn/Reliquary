package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RelicRedPaperclip extends CustomRelic {
    public static final String ID = "reliquary:RedPaperclip";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/redPaperclip.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/redPaperclip.png");

    static int REPLACES = 2;

    List<AbstractRelic> toRemove;

    public RelicRedPaperclip() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.relics.stream().filter(r -> r.tier == RelicTier.COMMON || r.tier == RelicTier.UNCOMMON).count() >= REPLACES;
    }

    @Override
    public void onEquip() {
        List<AbstractRelic> removables = AbstractDungeon.player.relics.stream().filter(r -> r.tier == RelicTier.COMMON || r.tier == RelicTier.UNCOMMON).collect(Collectors.toList());
        Collections.shuffle(removables, new Random(AbstractDungeon.miscRng.randomLong()));
        toRemove = removables.subList(0, Math.min(removables.size(), REPLACES));
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public void postUpdate() {
        // Losing relics during onEquip causes the relics list to get changed during a foreach...?
        if (toRemove != null) {
            for (AbstractRelic r : toRemove){
                AbstractDungeon.player.loseRelic(r.relicId);
            }
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
        return DESCRIPTIONS[0] + REPLACES + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRedPaperclip();
    }
}
