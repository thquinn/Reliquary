package relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.lang.reflect.Method;

public class RelicSkeletonKey extends RelicSolitaireBase {
    public static final String ID = "reliquary:SkeletonKey";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/skeletonKey.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/skeletonKey.png");

    public RelicSkeletonKey() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    public void upgradeCard(AbstractCard card) {
        flash();
        try {
            Method upgradeName = AbstractCard.class.getDeclaredMethod("upgradeName");
            upgradeName.setAccessible(true);
            upgradeName.invoke(card);
        } catch (Exception e) {
            ReliquaryLogger.error("RelicSkeletonKey failed to call upgradeName().");
        }
        switch (card.cardID) {
            default:
                ReliquaryLogger.error("RelicSkeletonKey tried to upgrade unknown card with ID: " + card.cardID);
        }
        card.initializeDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSkeletonKey();
    }
}
