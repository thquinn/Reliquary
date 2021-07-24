package relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.daily.mods.Endless;
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
    private final static int DESC_INDEX_BLUR = 9;
    private final static int DESC_INDEX_BULLET_TIME = 10;
    private final static int DESC_INDEX_CALCULATED_GAMBLE = 11;
    private final static int DESC_INDEX_CALTROPS = 12;
    private final static int DESC_INDEX_CATALYST = 13;
    private final static int DESC_INDEX_CHOKE = 14;
    private final static int DESC_INDEX_CONCENTRATE = 15;
    private final static int DESC_INDEX_CRIPPLING_CLOUD = 16;
    private final static int DESC_INDEX_DAGGER_SPRAY = 17;
    private final static int DESC_INDEX_DAGGER_THROW = 18;
    private final static int DESC_INDEX_DIE_DIE_DIE = 19;
    private final static int DESC_INDEX_DISTRACTION = 20;
    private final static int DESC_INDEX_DOPPELGANGER = 21;
    private final static int DESC_INDEX_ENDLESS_AGONY = 22;
    private final static int DESC_INDEX_ENVENOM = 23;
    private final static int DESC_INDEX_EVISCERATE = 24;

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
            case Blur.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BLUR];
                break;
            case BouncingFlask.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case BulletTime.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BULLET_TIME];
                break;
            case Burst.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case CalculatedGamble.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CALCULATED_GAMBLE];
                break;
            case Caltrops.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CALTROPS];
                break;
            case Catalyst.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CATALYST];
                break;
            case Choke.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CHOKE];
                break;
            case CloakAndDagger.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Concentrate.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CONCENTRATE];
                break;
            case CorpseExplosion.ID:
                changeBaseCost(card, 1);
                break;
            case CripplingPoison.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CRIPPLING_CLOUD];
                break;
            case DaggerSpray.ID:
                card.baseMagicNumber = 3;
                card.magicNumber = 3;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DAGGER_SPRAY];
                break;
            case DaggerThrow.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DAGGER_THROW];
                break;
            case Dash.ID:
                card.baseDamage += 3;
                card.baseBlock += 3;
                break;
            case DeadlyPoison.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Defend_Green.ID:
                card.baseBlock += 4;
                break;
            case Deflect.ID:
                card.baseBlock += 3;
                break;
            case DieDieDie.ID:
                card.exhaust = false;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DIE_DIE_DIE];
                break;
            case Distraction.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DISTRACTION];
                break;
            case DodgeAndRoll.ID:
                card.baseBlock += 2;
                break;
            case Doppelganger.ID:
                card.selfRetain = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DOPPELGANGER];
                break;
            case EndlessAgony.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ENDLESS_AGONY];
                break;
            case Envenom.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ENVENOM];
                break;
            case EscapePlan.ID:
                card.baseBlock += 2;
                break;
            case Eviscerate.ID:
                card.baseMagicNumber = 4;
                card.magicNumber = 4;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_EVISCERATE];
                break;
            case Expertise.ID:
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