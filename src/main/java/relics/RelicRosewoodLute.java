package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicRosewoodLute extends CustomRelic implements OnLoseBlockRelic {
    public static final String ID = "reliquary:RosewoodLute";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/rosewoodLute.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/rosewoodLute.png");

    public RelicRosewoodLute() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        if (i == AbstractDungeon.player.currentBlock) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.addPower(new EnergizedPower(AbstractDungeon.player, 1));
        }
        return i;
    }

    @Override
    public void onBlockBroken(AbstractCreature m) {
        super.onBlockBroken(m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRosewoodLute();
    }
}
