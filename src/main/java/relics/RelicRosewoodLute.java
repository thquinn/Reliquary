package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicRosewoodLute extends ReliquaryRelic implements OnLoseBlockRelic {
    public static final String ID = "reliquary:RosewoodLute";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/rosewoodLute.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/rosewoodLute.png");

    public RelicRosewoodLute() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }
    @Override
    public boolean isRetired() {
        return true;
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        AbstractPlayer p = AbstractDungeon.player;
        if (i == p.currentBlock) {
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, 1)));
        }
        return i;
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
