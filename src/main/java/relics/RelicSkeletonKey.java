package relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.lang.reflect.Method;

public class RelicSkeletonKey extends RelicSolitaireBase {
    public static final String ID = "reliquary:SkeletonKey";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/skeletonKey.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/skeletonKey.png");

    private final static int DESC_INDEX_A_THOUSAND_CUTS = 1;
    private final static int DESC_INDEX_ACCURACY = 2;
    private final static int DESC_INDEX_ADRENALINE = 3;
    private final static int DESC_INDEX_AFTER_IMAGE = 4;
    private final static int DESC_INDEX_ALCHEMIZE = 5;
    private final static int DESC_INDEX_ALL_OUT_ATTACK = 6;
    private final static int DESC_INDEX_BACKSTAB = 7;
    private final static int DESC_INDEX_BANE = 8;

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
            case AThousandCuts.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_A_THOUSAND_CUTS];
                break;
            case Accuracy.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ACCURACY];
                break;
            case Acrobatics.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Adrenaline.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ADRENALINE];
                break;
            case AfterImage.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_AFTER_IMAGE];
                break;
            case Alchemize.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ALCHEMIZE];
                break;
            case AllOutAttack.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ALL_OUT_ATTACK];
                break;
            case Backflip.ID:
                card.baseBlock += 3;
                break;
            case Backstab.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BACKSTAB];
                break;
            case Bane.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BANE];
                break;
            case BladeDance.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
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
