package relics;

import basemod.abstracts.CustomRelic;
import cards.colorless.CardVim;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicThirdArm extends ReliquaryRelic {
    public static final String ID = "reliquary:ThirdArm";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/thirdArm.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/thirdArm.png");

    public RelicThirdArm() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.player.drawPile.addToRandomSpot(new CardVim());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CardVim.COST + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicThirdArm();
    }
}
