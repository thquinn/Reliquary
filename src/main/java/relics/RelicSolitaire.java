package relics;

import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.PatchSolitaire;
import util.ReliquaryLogger;
import util.TextureLoader;

public class RelicSolitaire extends RelicSolitaireBase {
    public static final String ID = "reliquary:Solitaire";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/solitaire.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/solitaire.png");

    private final static int DESC_INDEX_ALPHA = 1;
    private final static int DESC_INDEX_BATTLE_HYMN = 2;
    private final static int DESC_INDEX_BLASPHEMY = 3;
    private final static int DESC_INDEX_CARVE_REALITY = 4;
    private final static int DESC_INDEX_COLLECT = 5;
    private final static int DESC_INDEX_CONJURE_BLADE = 6;
    private final static int DESC_INDEX_CRESCENDO = 7;
    private final static int DESC_INDEX_DECEIVE_REALITY = 8;
    private final static int DESC_INDEX_DEVA_FORM = 9;
    private final static int DESC_INDEX_FLYING_SLEEVES = 10;
    private final static int DESC_INDEX_FOREIGN_INFLUENCE = 11;
    private final static int DESC_INDEX_LESSON_LEARNED = 12;
    private final static int DESC_INDEX_MASTER_REALITY = 13;
    private final static int DESC_INDEX_MEDITATE = 14;
    private final static int DESC_INDEX_OMNISCIENCE = 15;
    private final static int DESC_INDEX_REACH_HEAVEN = 16;
    private final static int DESC_INDEX_SANDS_OF_TIME = 17;
    private final static int DESC_INDEX_SCRAWL = 18;
    private final static int DESC_INDEX_STUDY = 19;
    private final static int DESC_INDEX_SWIVEL = 20;
    private final static int DESC_INDEX_TRANQUILITY = 21;
    private final static int DESC_INDEX_VAULT = 22;
    private final static int DESC_INDEX_WORSHIP = 23;
    private final static int DESC_INDEX_ERUPTION = 24;

    public RelicSolitaire() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK, RelicType.PURPLE);
    }

    @Override
    @SuppressWarnings("DuplicateBranchesInSwitch")
    public void upgradeCard(AbstractCard card) {
        switch (card.cardID) {
            case Alpha.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ALPHA];
                break;
            case BattleHymn.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BATTLE_HYMN];
                break;
            case Blasphemy.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BLASPHEMY];
                break;
            case BowlingBash.ID:
                card.baseDamage += 3;
                break;
            case Brilliance.ID:
                changeBaseCost(card, 0);
                break;
            case CarveReality.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CARVE_REALITY];
                break;
            case Collect.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_COLLECT];
                break;
            case Conclude.ID:
                card.baseDamage += 4;
                break;
            case ConjureBlade.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CONJURE_BLADE];
                break;
            case Consecrate.ID:
                card.baseDamage += 3;
                break;
            case Crescendo.ID:
                card.baseMagicNumber = 3;
                card.magicNumber = 3;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CRESCENDO];
                break;
            case CrushJoints.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case CutThroughFate.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case DeceiveReality.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DECEIVE_REALITY];
                break;
            case Defend_Watcher.ID:
                card.baseBlock += 4;
                break;
            case DeusExMachina.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case DevaForm.ID:
                card.isInnate = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DEVA_FORM];
                break;
            case Devotion.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case EmptyBody.ID:
                card.baseBlock += 3;
                break;
            case EmptyFist.ID:
                changeBaseCost(card, 0);
                break;
            case EmptyMind.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Eruption.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ERUPTION];
                break;
            case Establishment.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Evaluate.ID:
                card.baseBlock += 4;
                break;
            case Fasting.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case FearNoEvil.ID:
                card.baseDamage += 3;
                break;
            case FlurryOfBlows.ID:
                card.baseDamage += 2;
                break;
            case FlyingSleeves.ID:
                card.baseMagicNumber = 3;
                card.magicNumber = 3;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_FLYING_SLEEVES];
                break;
            case FollowUp.ID:
                card.baseDamage += 2;
                break;
            case ForeignInfluence.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_FOREIGN_INFLUENCE];
                break;
            case Foresight.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Halt.ID:
                card.baseBlock += 1;
                card.magicNumber += 5;
                break;
            case Indignation.ID:
                card.baseMagicNumber += 5;
                card.magicNumber += 5;
                break;
            case InnerPeace.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Judgement.ID:
                card.baseMagicNumber += 10;
                card.magicNumber += 10;
                break;
            case JustLucky.ID:
                card.baseDamage += 1;
                card.baseBlock += 1;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case LessonLearned.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_LESSON_LEARNED];
                break;
            case LikeWater.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case MasterReality.ID:
                card.isInnate = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_MASTER_REALITY];
                break;
            case Meditate.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_MEDITATE];
                break;
            case MentalFortress.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Nirvana.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Omniscience.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_OMNISCIENCE];
                break;
            case Perseverance.ID:
                card.baseBlock += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Pray.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case PressurePoints.ID:
                card.baseMagicNumber += 3;
                card.magicNumber += 3;
                break;
            case Prostrate.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Protect.ID:
                card.baseBlock += 4;
                break;
            case Ragnarok.ID:
                card.baseDamage += 1;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case ReachHeaven.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_REACH_HEAVEN];
                break;
            case Rushdown.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.upgradedMagicNumber = true;
                break;
            case Sanctity.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.upgradedMagicNumber = true;
                break;
            case SandsOfTime.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SANDS_OF_TIME];
                break;
            case SashWhip.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Scrawl.ID:
                card.baseMagicNumber = 3;
                card.magicNumber = 3;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SCRAWL];
                break;
            case SignatureMove.ID:
                card.baseDamage += 10;
                break;
            case SimmeringFury.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case SpiritShield.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Strike_Purple.ID:
                card.baseDamage += 5;
                break;
            case Study.ID:
                card.cardsToPreview.upgrade();
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_STUDY];
                break;
            case Swivel.ID:
                card.baseMagicNumber = 4;
                card.magicNumber = 4;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SWIVEL];
                break;
            case TalkToTheHand.ID:
                card.baseBlock += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Tantrum.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case ThirdEye.ID:
                card.baseBlock += 2;
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Tranquility.ID:
                card.exhaust = false;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_TRANQUILITY];
                break;
            case Vault.ID:
                card.baseMagicNumber = 1;
                card.magicNumber = 1;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_VAULT];
                break;
            case Vigilance.ID:
                changeBaseCost(card, 1);
                break;
            case Wallop.ID:
                card.baseDamage += 3;
                break;
            case WaveOfTheHand.ID:
                changeBaseCost(card, 0);
                break;
            case Weave.ID:
                card.baseDamage += 2;
                break;
            case WheelKick.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.upgradedMagicNumber = true;
                break;
            case WindmillStrike.ID:
                card.baseDamage += 3;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Wish.ID:
                card.baseDamage += 1;
                card.baseBlock += 2;
                card.baseMagicNumber += 5;
                card.magicNumber += 5;
                break;
            case BecomeAlmighty.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case FameAndFortune.ID:
                card.baseMagicNumber += 5;
                card.magicNumber += 5;
                break;
            case LiveForever.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Worship.ID:
                card.rawDescription = String.format(DESCRIPTIONS[DESC_INDEX_WORSHIP], PatchSolitaire.PatchSolitaireWorship.INCREASE, PatchSolitaire.PatchSolitaireWorship.LIMIT);
                break;
            case WreathOfFlame.ID:
                card.baseMagicNumber += 3;
                card.magicNumber += 3;
                break;
            default:
                ReliquaryLogger.error("RelicSolitaire tried to upgrade unknown card with ID: " + card.cardID);
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
        return new RelicSolitaire();
    }
}
