package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import powers.TriumphPower;
import util.TextureLoader;

public class RelicDhvaja extends CustomRelic {
    public static final String ID = "reliquary:Dhvaja";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/dhvaja.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/dhvaja.png");

    public RelicDhvaja() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (newStance.ID.equals(DivinityStance.STANCE_ID)) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TriumphPower(AbstractDungeon.player, 1)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicDhvaja();
    }
}
