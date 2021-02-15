package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.PatchBigHammer;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.lang.reflect.Method;

public class RelicBigHammer extends CustomRelic {
    public static final String ID = "reliquary:BigHammer";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bigHammer.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bigHammer.png");

    private final static int DESC_INDEX_ANGER = 1;
    private final static int DESC_INDEX_ARMAMENTS = 2;
    private final static int DESC_INDEX_BARRICADE = 3;
    private final static int DESC_INDEX_BERSERK = 4;
    private final static int DESC_INDEX_BLOODLETTING = 5;
    public final static int DESC_INDEX_BODY_SLAM = 6;
    private final static int DESC_INDEX_BRUTALITY = 7;
    private final static int DESC_INDEX_COMBUST = 8;
    private final static int DESC_INDEX_CORRUPTION = 9;
    private final static int DESC_INDEX_DARK_EMBRACE = 10;
    private final static int DESC_INDEX_DROPKICK = 11;
    private final static int DESC_INDEX_DUAL_WIELD = 12;
    private final static int DESC_INDEX_ENTRENCH = 13;
    private final static int DESC_INDEX_EVOLVE = 14;
    private final static int DESC_INDEX_EXHUME = 15;
    private final static int DESC_INDEX_FLEX = 16;
    private final static int DESC_INDEX_HAVOC = 17;
    private final static int DESC_INDEX_INFERNAL_BLADE = 18;
    private final static int DESC_INDEX_LIMIT_BREAK = 19;
    private final static int DESC_INDEX_OFFERING = 20;
    private final static int DESC_INDEX_RECKLESS_CHARGE = 21;
    private final static int DESC_INDEX_SEEING_RED = 22;
    private final static int DESC_INDEX_SENTINEL = 23;
    private final static int DESC_INDEX_THUNDERCLAP = 24;
    private final static int DESC_INDEX_TRUE_GRIT = 25;
    private final static int DESC_INDEX_WHIRLWIND = 26;

    public RelicBigHammer() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    public void upgradeCard(AbstractCard card) {
        flash();
        try {
            Method upgradeName = AbstractCard.class.getDeclaredMethod("upgradeName");
            upgradeName.setAccessible(true);
            upgradeName.invoke(card);
        } catch (Exception e) {
            ReliquaryLogger.error("RelicBigHammer failed to call upgradeName().");
        }
        switch (card.cardID) {
            case Anger.ID:
                card.baseMagicNumber = 1;
                card.magicNumber = 1;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ANGER];
                break;
            case Armaments.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ARMAMENTS];
                break;
            case Barricade.ID:
                card.isInnate = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BARRICADE];
                break;
            case Bash.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case BattleTrance.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Berserk.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BERSERK];
                break;
            case BloodForBlood.ID:
                card.baseDamage += 4;
                break;
            case Bloodletting.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BLOODLETTING];
                break;
            case Bludgeon.ID:
                card.baseDamage += 10;
                break;
            case BodySlam.ID:
                card.rawDescription = String.format(DESCRIPTIONS[DESC_INDEX_BODY_SLAM], PatchBigHammer.PatchBigHammerBodySlam.PERCENTAGE);
                break;
            case Brutality.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_BRUTALITY];
                break;
            case BurningPact.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Carnage.ID:
                card.baseDamage += 8;
                break;
            case Clash.ID:
                card.baseDamage += 4;
                break;
            case Cleave.ID:
                card.baseDamage += 3;
                break;
            case Clothesline.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Combust.ID:
                card.rawDescription = String.format(DESCRIPTIONS[DESC_INDEX_COMBUST], PatchBigHammer.PatchBigHammerCombust.GAIN_PER_TURN);
                break;
            case Corruption.ID:
                card.isInnate = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_CORRUPTION];
                break;
            case DarkEmbrace.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DARK_EMBRACE];
                break;
            case Defend_Red.ID:
                card.baseBlock += 4;
                break;
            case DemonForm.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Disarm.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case DoubleTap.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Dropkick.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DROPKICK];
                break;
            case DualWield.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_DUAL_WIELD];
                break;
            case Entrench.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_ENTRENCH];
                break;
            case Evolve.ID:
                card.rawDescription = String.format(DESCRIPTIONS[DESC_INDEX_EVOLVE], PatchBigHammer.PatchBigHammerEvolve.HAND_SIZE_INCREASE);
                break;
            case Exhume.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_EXHUME];
                break;
            case Feed.ID:
                card.baseDamage += 2;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case FeelNoPain.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case FiendFire.ID:
                card.baseDamage += 3;
                break;
            case FireBreathing.ID:
                card.baseMagicNumber += 4;
                card.magicNumber += 4;
                break;
            case FlameBarrier.ID:
                card.baseBlock += 4;
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Flex.ID:
                card.rawDescription = String.format(DESCRIPTIONS[DESC_INDEX_FLEX], PatchBigHammer.PatchBigHammerFlex.HYDRATION + 1);
                break;
            case GhostlyArmor.ID:
                card.baseBlock += 3;
                break;
            case Havoc.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_HAVOC];
                break;
            case Headbutt.ID:
                card.baseDamage += 3;
                break;
            case HeavyBlade.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Hemokinesis.ID:
                RelicSolitaire.upgradeBaseCost(card, 0);
                break;
            case Immolate.ID:
                card.baseDamage += 7;
                break;
            case Impervious.ID:
                RelicSolitaire.upgradeBaseCost(card, 1);
                break;
            case InfernalBlade.ID:
                card.exhaust = false;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_INFERNAL_BLADE];
                break;
            case Inflame.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Intimidate.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case IronWave.ID:
                card.baseDamage += 2;
                card.baseBlock += 2;
                break;
            case Juggernaut.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case LimitBreak.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_LIMIT_BREAK];
                break;
            case Metallicize.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Offering.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_OFFERING];
                break;
            case PerfectedStrike.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case PommelStrike.ID:
                card.baseDamage += 1;
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case PowerThrough.ID:
                card.baseBlock += 5;
                break;
            case Pummel.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Rage.ID:
                card.baseMagicNumber += 2;
                card.magicNumber += 2;
                break;
            case Rampage.ID:
                card.baseMagicNumber += 3;
                card.magicNumber += 3;
                break;
            case Reaper.ID:
                RelicSolitaire.upgradeBaseCost(card, 1);
                break;
            case RecklessCharge.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_RECKLESS_CHARGE];
                break;
            case SecondWind.ID:
                card.baseBlock += 2;
                break;
            case SeeingRed.ID:
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SEEING_RED];
                break;
            case Sentinel.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_SENTINEL];
                break;
            case SeverSoul.ID:
                card.baseDamage += 6;
                break;
            case Shockwave.ID:
                RelicSolitaire.upgradeBaseCost(card, 1);
                break;
            case ShrugItOff.ID:
                card.baseBlock += 3;
                break;
            case SpotWeakness.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Strike_Red.ID:
                card.baseDamage += 5;
                break;
            case SwordBoomerang.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case ThunderClap.ID:
                card.baseMagicNumber = 2;
                card.magicNumber = 2;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_THUNDERCLAP];
                break;
            case TrueGrit.ID:
                card.baseMagicNumber = 3;
                card.magicNumber = 3;
                card.upgradedMagicNumber = true;
                card.rawDescription = DESCRIPTIONS[DESC_INDEX_TRUE_GRIT];
                break;
            case TwinStrike.ID:
                RelicSolitaire.upgradeBaseCost(card, 0);
                break;
            case Uppercut.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Warcry.ID:
                card.baseMagicNumber += 1;
                card.magicNumber += 1;
                break;
            case Whirlwind.ID:
                card.rawDescription = String.format(DESCRIPTIONS[DESC_INDEX_WHIRLWIND], PatchBigHammer.PatchBigHammerWhirlwind.EXTRA);
                break;
            case WildStrike.ID:
                card.baseDamage += 5;
                break;
            default:
                ReliquaryLogger.error("RelicBigHammer tried to upgrade unknown card with ID: " + card.cardID);
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
        return new RelicBigHammer();
    }
}
