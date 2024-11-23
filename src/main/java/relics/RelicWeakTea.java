package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import powers.ReduceColorlessCostPower;
import util.TextureLoader;

public class RelicWeakTea extends ReliquaryRelic {
    public static final String ID = "reliquary:WeakTea";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/weakTea.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/weakTea.png");

    static int AMOUNT = 2;

    public RelicWeakTea() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new ReduceColorlessCostPower(p, AMOUNT)));
    }

    @Override
    public String getUpdatedDescription() {
        if (AMOUNT == 1) {
            return DESCRIPTIONS[0];
        }
        return DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicWeakTea();
    }
}
