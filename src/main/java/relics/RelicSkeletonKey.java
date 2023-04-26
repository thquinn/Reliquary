package relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;
import util.TextureLoader;

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
    private final static int DESC_INDEX_GLASS_KNIFE = 25;
    public final static int DESC_INDEX_GRAND_FINALE = 26;
    // 27-28 are UI strings for Grand Finale++
    public final static int DESC_INDEX_HEEL_HOOK = 29;
    public final static int DESC_INDEX_INFINITE_BLADES = 30;
    public final static int DESC_INDEX_MALAISE = 31;
    public final static int DESC_INDEX_NIGHTMARE = 32;
    public final static int DESC_INDEX_OUTMANEUVER = 33;
    public final static int DESC_INDEX_PHANTASMAL_KILLER = 34;
    public final static int DESC_INDEX_PIERCING_WAIL = 35;
    public final static int DESC_INDEX_PREDATOR = 36;
    public final static int DESC_INDEX_RIDDLE_WITH_HOLES = 37;
    public final static int DESC_INDEX_SETUP = 38;
    public final static int DESC_INDEX_SKEWER = 39;
    public final static int DESC_INDEX_STORM_OF_STEEL = 40;
    public final static int DESC_INDEX_TACTICIAN = 41;
    public final static int DESC_INDEX_TERROR = 42;
    public final static int DESC_INDEX_TOOLS_OF_THE_TRADE = 43;
    public final static int DESC_INDEX_UNLOAD = 44;

    public RelicSkeletonKey() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    @SuppressWarnings("DuplicateBranchesInSwitch")
    public void upgradeCard(AbstractCard card) {
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
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
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
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
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
            case Finisher.ID:
                changeBaseCost(card, 0);
                break;
            case Flechettes.ID:
                changeBaseCost(card, 0);
                break;
            case FlyingKnee.ID:
                card.baseDamage += 3;
                break;
            case Footwork.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case GlassKnife.ID:
                card.baseMagicNumber = 1;
                card.magicNumber = 1;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_GLASS_KNIFE];
                break;
            case GrandFinale.ID:
                card.baseDamage += 10;
                card.baseMagicNumber = 1;
                card.magicNumber = 1;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_GRAND_FINALE];
                break;
            case HeelHook.ID:
                card.baseDamage += 3;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_HEEL_HOOK];
                break;
            case InfiniteBlades.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_INFINITE_BLADES];
                break;
            case LegSweep.ID:
                card.baseBlock += 3;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Malaise.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_MALAISE];
                break;
            case MasterfulStab.ID:
                card.baseDamage += 4;
                break;
            case Neutralize.ID:
                card.baseDamage += 1;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Nightmare.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_NIGHTMARE];
                break;
            case NoxiousFumes.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Outmaneuver.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_OUTMANEUVER];
                break;
            case PhantasmalKiller.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_PHANTASMAL_KILLER];
                break;
            case PiercingWail.ID:
                card.exhaust = false;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_PIERCING_WAIL];
                break;
            case PoisonedStab.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Predator.ID:
                card.baseDamage += 5;
                card.baseMagicNumber = 3;
                card.magicNumber = 3;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_PREDATOR];
                break;
            case Prepared.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case QuickSlash.ID:
                card.baseDamage += 4;
                break;
            case Reflex.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case RiddleWithHoles.ID:
                card.baseMagicNumber = 6;
                card.magicNumber = 6;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_RIDDLE_WITH_HOLES];
                break;
            case Setup.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SETUP];
                break;
            case Shiv.ID:
                card.baseDamage += 2;
                break;
            case Skewer.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SKEWER];
                break;
            case Slice.ID:
                card.baseDamage += 3;
                break;
            case SneakyStrike.ID:
                card.baseDamage += 4;
                break;
            case StormOfSteel.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_STORM_OF_STEEL];
                break;
            case Strike_Green.ID:
                card.baseDamage += 5;
                break;
            case SuckerPunch.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Survivor.ID:
                card.baseBlock += 3;
                break;
            case Tactician.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_TACTICIAN];
                break;
            case Terror.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_TERROR];
                break;
            case ToolsOfTheTrade.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_TOOLS_OF_THE_TRADE];
                break;
            case Unload.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_UNLOAD];
                break;
            case WellLaidPlans.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case WraithForm.ID:
                changeBaseCost(card, 2);
                break;
            default:
                ReliquaryLogger.error("RelicSkeletonKey tried to upgrade unknown card with ID: " + card.cardID);
                return;
        }
        super.upgradeCard(card);
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
