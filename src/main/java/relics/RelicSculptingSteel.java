package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import util.TextureLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RelicSculptingSteel extends CustomRelic {
    public static final String ID = "reliquary:SculptingSteel";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/sculptingSteel.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/sculptingSteel.png");

    public RelicSculptingSteel() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public boolean canSpawn() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (COPYABLE_RELICS.contains(r.relicId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onEquip() {
        int count = (int)AbstractDungeon.player.relics.stream().filter(r -> COPYABLE_RELICS.contains(r.relicId)).count();
        int selector = MathUtils.random(count - 1);
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (COPYABLE_RELICS.contains(relic.relicId)) {
                if (selector == 0) {
                    AbstractRelic copy = relic.makeCopy();
                    copy.instantObtain();
                    copy.flash();
                    break;
                }
                selector--;
            }
        }
        AbstractDungeon.player.loseRelic(ID);
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

    private final Set<String> COPYABLE_RELICS = new HashSet<>(Arrays.asList(
            // Reliquary
            RelicBoilingFlask.ID,
            RelicBookmark.ID,
            RelicBoomerang.ID,
            RelicExpiredCoupon.ID,
            RelicFirecrackers.ID,
            RelicHotPoker.ID,
            RelicIvoryTrinket.ID,
            RelicJackalopeAntler.ID,
            RelicMedicineBall.ID,
            RelicOuijaBoard.ID,
            RelicPorcupineQuills.ID,
            RelicRosewoodLute.ID,
            RelicTuningFork.ID,
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
            CentennialPuzzle.ID,
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
            Orrery.ID,
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
