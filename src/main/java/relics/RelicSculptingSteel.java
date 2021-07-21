package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.util.*;
import java.util.stream.Collectors;

public class RelicSculptingSteel extends CustomRelic {
    public static final String ID = "reliquary:SculptingSteel";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/sculptingSteel.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/sculptingSteel.png");

    public RelicSculptingSteel() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.relics.stream().anyMatch(r -> isAppropriateRarity(r) && COPYABLE_RELICS.contains(r.relicId));
    }

    @Override
    public void onEquip() {
        List<AbstractRelic> copyables = AbstractDungeon.player.relics.stream().filter(r -> isAppropriateRarity(r) && COPYABLE_RELICS.contains(r.relicId)).collect(Collectors.toList());
        if (copyables.isEmpty()) {
            ReliquaryLogger.log("no copyables");
            return;
        }
        Collections.shuffle(copyables, new Random(AbstractDungeon.miscRng.randomLong()));
        AbstractRelic copy = copyables.get(0).makeCopy();
        copy.instantObtain();
        copy.flash();
        AbstractDungeon.player.loseRelic(ID);
    }

    static boolean isAppropriateRarity(AbstractRelic relic) {
        return relic.tier == RelicTier.COMMON || relic.tier == RelicTier.UNCOMMON || relic.tier == RelicTier.RARE || relic.tier == RelicTier.SHOP;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSculptingSteel();
    }

    // This is a set of all Reliquary and base-game relics that:
    //      - function correctly and straightforwardly in multiples, and
    //      - if they have downsides, it is a compounding downside (gets more punishing in multiples)
    // Not all of these will actually be copied by Sculpting Steel; they are filtered further in canSpawn and onEquip.
    private final Set<String> COPYABLE_RELICS = new HashSet<>(Arrays.asList(
            // Reliquary
            RelicAluminiumFoil.ID,
            RelicBallBearing.ID,
            RelicBoilingFlask.ID,
            RelicBookmark.ID,
            RelicBoomerang.ID,
            RelicBuckler.ID,
            RelicConveyor.ID,
            RelicCraggleroot.ID,
            RelicCrutches.ID,
            RelicDhvaja.ID,
            RelicEmber.ID,
            RelicExpiredCoupon.ID,
            RelicFirecrackers.ID,
            RelicHotPoker.ID,
            RelicIvoryTrinket.ID,
            RelicJackalopeAntler.ID,
            RelicKillPill.ID,
            RelicKnifeBlock.ID,
            RelicMedicineBall.ID,
            RelicOuijaBoard.ID,
            RelicPorcupineQuills.ID,
            RelicRosewoodLute.ID,
            RelicSharkskinSheath.ID,
            RelicSpinner.ID,
            RelicTamtam.ID,
            RelicThumbDrive.ID,
            RelicTridentHead.ID,
            RelicTuningFork.ID,
            RelicVitrine.ID,
            RelicWeakTea.ID,
            RelicWritOfMandamus.ID,
            // base game
            Abacus.ID,
            Akabeko.ID,
            Anchor.ID,
            AncientTeaSet.ID,
            BagOfMarbles.ID,
            BagOfPreparation.ID,
            BirdFacedUrn.ID,
            BlackBlood.ID,
            BloodVial.ID,
            BloodyIdol.ID,
            Brimstone.ID,
            BronzeScales.ID,
            BurningBlood.ID,
            CallingBell.ID,
            CaptainsWheel.ID,
            Cauldron.ID,
            CeramicFish.ID,
            ChampionsBelt.ID,
            CharonsAshes.ID,
            CloakClasp.ID,
            ClockworkSouvenir.ID,
            CrackedCore.ID,
            Damaru.ID,
            DarkstonePeriapt.ID,
            DataDisk.ID,
            DeadBranch.ID,
            DollysMirror.ID,
            DuVuDoll.ID,
            Duality.ID,
            EmotionChip.ID,
            EmptyCage.ID,
            Enchiridion.ID,
            EternalFeather.ID,
            FaceOfCleric.ID,
            FossilizedHelix.ID,
            FrozenCore.ID,
            GremlinHorn.ID,
            HandDrill.ID,
            HappyFlower.ID,
            HolyWater.ID,
            HornCleat.ID,
            HoveringKite.ID,
            InkBottle.ID,
            Inserter.ID,
            Kunai.ID,
            Lantern.ID,
            LetterOpener.ID,
            Mango.ID,
            MeatOnTheBone.ID,
            Melange.ID,
            MercuryHourglass.ID,
            MummifiedHand.ID,
            MutagenicStrength.ID,
            NilrysCodex.ID,
            NinjaScroll.ID,
            NuclearBattery.ID,
            Nunchaku.ID,
            OddlySmoothStone.ID,
            OldCoin.ID,
            OrnamentalFan.ID,
            Pantograph.ID,
            Pear.ID,
            PenNib.ID,
            PhilosopherStone.ID,
            Pocketwatch.ID,
            PureWater.ID,
            QuestionCard.ID,
            RedMask.ID,
            RedSkull.ID,
            RingOfTheSerpent.ID,
            RunicCapacitor.ID,
            RunicCube.ID,
            SelfFormingClay.ID,
            Shuriken.ID,
            SlaversCollar.ID,
            Sling.ID,
            SnakeRing.ID,
            SsserpentHead.ID,
            StoneCalendar.ID,
            Strawberry.ID,
            StrikeDummy.ID,
            Sundial.ID,
            SymbioticVirus.ID,
            ThreadAndNeedle.ID,
            Tingsha.ID,
            TinyHouse.ID,
            Toolbox.ID,
            ToughBandages.ID,
            ToyOrnithopter.ID,
            TungstenRod.ID,
            TwistedFunnel.ID,
            Vajra.ID,
            VioletLotus.ID,
            Waffle.ID,
            WarPaint.ID,
            WarpedTongs.ID,
            Whetstone.ID,
            WristBlade.ID
    ));
}
