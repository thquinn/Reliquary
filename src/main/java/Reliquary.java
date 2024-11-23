import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.ReliquaryCard;
import cards.cookie.CardCookieToutSweet;
import cards.cookie.CookieStatics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import orbs.OrbBias;
import orbs.OrbData;
import powers.*;
import relics.*;
import stances.AtonementStance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class Reliquary implements AddAudioSubscriber, EditCardsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostInitializeSubscriber,
        OnCardUseSubscriber, OnPlayerTurnStartPostDrawSubscriber, OnStartBattleSubscriber, PostUpdateSubscriber {
    public static final String ID = "reliquary";
    public static final String CONFIG_NAME = "reliconfig";
    public static final String CONFIG_USE_RETIRED_RELICS = "use_retired_relics";

    public static void initialize() {
        BaseMod.subscribe(new Reliquary());
        BaseMod.addColor(
                CookieStatics.RELIQUARY_COOKIE,
                CookieStatics.COLOR,
                CookieStatics.CARD_BACKS_ATTACK_SMALL[0][0], CookieStatics.CARD_BACKS_SKILL_SMALL[0][0], CookieStatics.CARD_BACKS_POWER_SMALL[0][0],
                CookieStatics.CARD_BACK_ENERGY_SMALL,
                CookieStatics.CARD_BACKS_ATTACK_LARGE[0][0], CookieStatics.CARD_BACKS_SKILL_LARGE[0][0], CookieStatics.CARD_BACKS_POWER_LARGE[0][0],
                CookieStatics.CARD_BACK_ENERGY_LARGE, CookieStatics.CARD_BACK_ENERGY_TEXT
        );
        try {
            Properties defaults = new Properties();
            defaults.setProperty(CONFIG_USE_RETIRED_RELICS, "false");
            SpireConfig config = new SpireConfig(ID, CONFIG_NAME, defaults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(ID)
                .packageFilter(RelicBoilingFlask.class)
                .setDefaultSeen(true);
        BaseMod.addRelic(new RelicAerogel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicAluminiumFoil(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBallBearing(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBellows(), RelicType.RED);
        BaseMod.addRelic(new RelicBigHammer(), RelicType.RED);
        BaseMod.addRelic(new RelicBloodFilter(), RelicType.RED);
        BaseMod.addRelic(new RelicBloodSugar(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBoilingFlask(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBookmark(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBoomerang(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBouncer(), RelicType.GREEN);
        BaseMod.addRelic(new RelicBrokenClock(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBuckler(), RelicType.SHARED);
        BaseMod.addRelic(new RelicCitrusReamer(), RelicType.SHARED);
        BaseMod.addRelic(new RelicConveyor(), RelicType.BLUE);
        BaseMod.addRelic(new RelicCookieJar(), RelicType.SHARED);
        BaseMod.addRelic(new RelicCraggleroot(), RelicType.SHARED);
        BaseMod.addRelic(new RelicCrucible(), RelicType.SHARED);
        BaseMod.addRelic(new RelicCrusadersMap(), RelicType.SHARED);
        BaseMod.addRelic(new RelicCrutches(), RelicType.SHARED);
        BaseMod.addRelic(new RelicDhvaja(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicElizabethanCollar(), RelicType.SHARED);
        BaseMod.addRelic(new RelicEmber(), RelicType.SHARED);
        BaseMod.addRelic(new RelicEXA(), RelicType.BLUE);
        BaseMod.addRelic(new RelicExpiredCoupon(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFeatherDuster(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFerryPass(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFirecrackers(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFishingReel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFrayedKnot(), RelicType.GREEN);
        BaseMod.addRelic(new RelicFreeSamples(), RelicType.SHARED);
        BaseMod.addRelic(new RelicGummyVitamins(), RelicType.SHARED);
        BaseMod.addRelic(new RelicHardlight(), RelicType.RED);
        BaseMod.addRelic(new RelicHolyGrail(), RelicType.SHARED);
        BaseMod.addRelic(new RelicHotPoker(), RelicType.SHARED);
        BaseMod.addRelic(new RelicIceCubeTray(), RelicType.SHARED);
        BaseMod.addRelic(new RelicIridiumChain(), RelicType.SHARED);
        BaseMod.addRelic(new RelicIvoryTrinket(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicJackalopeAntler(), RelicType.SHARED);
        BaseMod.addRelic(new RelicKillPill(), RelicType.GREEN);
        BaseMod.addRelic(new RelicKinkedSpring(), RelicType.SHARED);
        BaseMod.addRelic(new RelicKnifeBlock(), RelicType.GREEN);
        BaseMod.addRelic(new RelicKnoch(), RelicType.SHARED);
        BaseMod.addRelic(new RelicLeadDart(), RelicType.SHARED);
        BaseMod.addRelic(new RelicLoveEmittingDiode(), RelicType.BLUE);
        BaseMod.addRelic(new RelicMedicineBall(), RelicType.RED);
        BaseMod.addRelic(new RelicMudwinsCradle(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicNewFriend(), RelicType.SHARED);
        BaseMod.addRelic(new RelicOuijaBoard(), RelicType.SHARED);
        BaseMod.addRelic(new RelicOvenMitt(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPaperSnowflake(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPartyBalloon(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPeacockShield(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicPlasmaLobster(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPorcupineQuills(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPrayerBox(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicPrincelyHelmet(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPromissoryNotes(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPurpleTingedLeaf(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPushpin(), RelicType.GREEN);
        BaseMod.addRelic(new RelicRabbitEars(), RelicType.BLUE);
        BaseMod.addRelic(new RelicRadioactivePellet(), RelicType.GREEN);
        BaseMod.addRelic(new RelicRattleCoin(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRedCape(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRedPaperclip(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRoseTintedGlasses(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRosewoodLute(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRunicRemote(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRustamsPendant(), RelicType.SHARED);
        BaseMod.addRelic(new RelicQuartzCube(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSculptingSteel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSharkskinSheath(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSheepsEyeMarble(), RelicType.SHARED);
        BaseMod.addRelic(new RelicShortFuse(), RelicType.BLUE);
        BaseMod.addRelic(new RelicShortStraw(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSideboard(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSilkGlove(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSkeletonKey(), RelicType.GREEN);
        BaseMod.addRelic(new RelicSod(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSolitaire(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicSoularoid(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSpinner(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSpiralShell(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSpireOfHannoy(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSplatula(), RelicType.SHARED);
        BaseMod.addRelic(new RelicStiletto(), RelicType.SHARED);
        BaseMod.addRelic(new RelicStimpack(), RelicType.RED);
        BaseMod.addRelic(new RelicTamtam(), RelicType.RED);
        BaseMod.addRelic(new RelicTankTop(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTatteredRug(), RelicType.SHARED);
        BaseMod.addRelic(new RelicThinkingCap(), RelicType.SHARED);
        BaseMod.addRelic(new RelicThirdArm(), RelicType.SHARED);
        BaseMod.addRelic(new RelicThumbDrive(), RelicType.BLUE);
        BaseMod.addRelic(new RelicToyRocket(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTrafficCone(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTrainingWheel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTridentHead(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTuningFork(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTwinPearls(), RelicType.SHARED);
        BaseMod.addRelic(new RelicUnderdogBone(), RelicType.SHARED);
        BaseMod.addRelic(new RelicVitrine(), RelicType.SHARED);
        BaseMod.addRelic(new RelicWeakTea(), RelicType.SHARED);
        BaseMod.addRelic(new RelicWitchyDice(), RelicType.SHARED);
        BaseMod.addRelic(new RelicWritOfMandamus(), RelicType.SHARED);
        BaseMod.addRelic(new RelicZipperTab(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(RelicAerogel.ID);
        UnlockTracker.markRelicAsSeen(RelicAluminiumFoil.ID);
        UnlockTracker.markRelicAsSeen(RelicBallBearing.ID);
        UnlockTracker.markRelicAsSeen(RelicBellows.ID);
        UnlockTracker.markRelicAsSeen(RelicBigHammer.ID);
        UnlockTracker.markRelicAsSeen(RelicBloodFilter.ID);
        UnlockTracker.markRelicAsSeen(RelicBloodSugar.ID);
        UnlockTracker.markRelicAsSeen(RelicBoilingFlask.ID);
        UnlockTracker.markRelicAsSeen(RelicBookmark.ID);
        UnlockTracker.markRelicAsSeen(RelicBoomerang.ID);
        UnlockTracker.markRelicAsSeen(RelicBouncer.ID);
        UnlockTracker.markRelicAsSeen(RelicBrokenClock.ID);
        UnlockTracker.markRelicAsSeen(RelicBuckler.ID);
        UnlockTracker.markRelicAsSeen(RelicCitrusReamer.ID);
        UnlockTracker.markRelicAsSeen(RelicConveyor.ID);
        UnlockTracker.markRelicAsSeen(RelicCookieJar.ID);
        UnlockTracker.markRelicAsSeen(RelicCraggleroot.ID);
        UnlockTracker.markRelicAsSeen(RelicCrucible.ID);
        UnlockTracker.markRelicAsSeen(RelicCrusadersMap.ID);
        UnlockTracker.markRelicAsSeen(RelicCrutches.ID);
        UnlockTracker.markRelicAsSeen(RelicDhvaja.ID);
        UnlockTracker.markRelicAsSeen(RelicElizabethanCollar.ID);
        UnlockTracker.markRelicAsSeen(RelicEmber.ID);
        UnlockTracker.markRelicAsSeen(RelicEXA.ID);
        UnlockTracker.markRelicAsSeen(RelicExpiredCoupon.ID);
        UnlockTracker.markRelicAsSeen(RelicFeatherDuster.ID);
        UnlockTracker.markRelicAsSeen(RelicFerryPass.ID);
        UnlockTracker.markRelicAsSeen(RelicFirecrackers.ID);
        UnlockTracker.markRelicAsSeen(RelicFishingReel.ID);
        UnlockTracker.markRelicAsSeen(RelicFrayedKnot.ID);
        UnlockTracker.markRelicAsSeen(RelicFreeSamples.ID);
        UnlockTracker.markRelicAsSeen(RelicGummyVitamins.ID);
        UnlockTracker.markRelicAsSeen(RelicHardlight.ID);
        UnlockTracker.markRelicAsSeen(RelicHotPoker.ID);
        UnlockTracker.markRelicAsSeen(RelicIceCubeTray.ID);
        UnlockTracker.markRelicAsSeen(RelicIridiumChain.ID);
        UnlockTracker.markRelicAsSeen(RelicIvoryTrinket.ID);
        UnlockTracker.markRelicAsSeen(RelicJackalopeAntler.ID);
        UnlockTracker.markRelicAsSeen(RelicKillPill.ID);
        UnlockTracker.markRelicAsSeen(RelicKinkedSpring.ID);
        UnlockTracker.markRelicAsSeen(RelicKnifeBlock.ID);
        UnlockTracker.markRelicAsSeen(RelicKnoch.ID);
        UnlockTracker.markRelicAsSeen(RelicLeadDart.ID);
        UnlockTracker.markRelicAsSeen(RelicLoveEmittingDiode.ID);
        UnlockTracker.markRelicAsSeen(RelicMedicineBall.ID);
        UnlockTracker.markRelicAsSeen(RelicMudwinsCradle.ID);
        UnlockTracker.markRelicAsSeen(RelicNewFriend.ID);
        UnlockTracker.markRelicAsSeen(RelicOuijaBoard.ID);
        UnlockTracker.markRelicAsSeen(RelicOvenMitt.ID);
        UnlockTracker.markRelicAsSeen(RelicPaperSnowflake.ID);
        UnlockTracker.markRelicAsSeen(RelicPartyBalloon.ID);
        UnlockTracker.markRelicAsSeen(RelicPeacockShield.ID);
        UnlockTracker.markRelicAsSeen(RelicPlasmaLobster.ID);
        UnlockTracker.markRelicAsSeen(RelicPorcupineQuills.ID);
        UnlockTracker.markRelicAsSeen(RelicPrayerBox.ID);
        UnlockTracker.markRelicAsSeen(RelicPrincelyHelmet.ID);
        UnlockTracker.markRelicAsSeen(RelicPromissoryNotes.ID);
        UnlockTracker.markRelicAsSeen(RelicPurpleTingedLeaf.ID);
        UnlockTracker.markRelicAsSeen(RelicPushpin.ID);
        UnlockTracker.markRelicAsSeen(RelicRabbitEars.ID);
        UnlockTracker.markRelicAsSeen(RelicRadioactivePellet.ID);
        UnlockTracker.markRelicAsSeen(RelicRattleCoin.ID);
        UnlockTracker.markRelicAsSeen(RelicRedCape.ID);
        UnlockTracker.markRelicAsSeen(RelicRedPaperclip.ID);
        UnlockTracker.markRelicAsSeen(RelicRoseTintedGlasses.ID);
        UnlockTracker.markRelicAsSeen(RelicRunicRemote.ID);
        UnlockTracker.markRelicAsSeen(RelicRustamsPendant.ID);
        UnlockTracker.markRelicAsSeen(RelicQuartzCube.ID);
        UnlockTracker.markRelicAsSeen(RelicSculptingSteel.ID);
        UnlockTracker.markRelicAsSeen(RelicSharkskinSheath.ID);
        UnlockTracker.markRelicAsSeen(RelicSheepsEyeMarble.ID);
        UnlockTracker.markRelicAsSeen(RelicShortFuse.ID);
        UnlockTracker.markRelicAsSeen(RelicShortStraw.ID);
        UnlockTracker.markRelicAsSeen(RelicSideboard.ID);
        UnlockTracker.markRelicAsSeen(RelicSilkGlove.ID);
        UnlockTracker.markRelicAsSeen(RelicSkeletonKey.ID);
        UnlockTracker.markRelicAsSeen(RelicSod.ID);
        UnlockTracker.markRelicAsSeen(RelicSolitaire.ID);
        UnlockTracker.markRelicAsSeen(RelicSoularoid.ID);
        UnlockTracker.markRelicAsSeen(RelicSpinner.ID);
        UnlockTracker.markRelicAsSeen(RelicSpiralShell.ID);
        UnlockTracker.markRelicAsSeen(RelicSpireOfHannoy.ID);
        UnlockTracker.markRelicAsSeen(RelicSplatula.ID);
        UnlockTracker.markRelicAsSeen(RelicStiletto.ID);
        UnlockTracker.markRelicAsSeen(RelicStimpack.ID);
        UnlockTracker.markRelicAsSeen(RelicTamtam.ID);
        UnlockTracker.markRelicAsSeen(RelicTankTop.ID);
        UnlockTracker.markRelicAsSeen(RelicTatteredRug.ID);
        UnlockTracker.markRelicAsSeen(RelicThinkingCap.ID);
        UnlockTracker.markRelicAsSeen(RelicThirdArm.ID);
        UnlockTracker.markRelicAsSeen(RelicThumbDrive.ID);
        UnlockTracker.markRelicAsSeen(RelicToyRocket.ID);
        UnlockTracker.markRelicAsSeen(RelicTrafficCone.ID);
        UnlockTracker.markRelicAsSeen(RelicTrainingWheel.ID);
        UnlockTracker.markRelicAsSeen(RelicTridentHead.ID);
        UnlockTracker.markRelicAsSeen(RelicTwinPearls.ID);
        UnlockTracker.markRelicAsSeen(RelicUnderdogBone.ID);
        UnlockTracker.markRelicAsSeen(RelicVitrine.ID);
        UnlockTracker.markRelicAsSeen(RelicWeakTea.ID);
        UnlockTracker.markRelicAsSeen(RelicWitchyDice.ID);
        UnlockTracker.markRelicAsSeen(RelicWritOfMandamus.ID);
        UnlockTracker.markRelicAsSeen(RelicZipperTab.ID);

        boolean useRetiredRelics = false;
        try {
            SpireConfig config = new SpireConfig(ID, CONFIG_NAME);
            useRetiredRelics = config.getBool(CONFIG_USE_RETIRED_RELICS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (useRetiredRelics) {
            BaseMod.addRelic(new RelicFingerTrap(), RelicType.SHARED);
            BaseMod.addRelic(new RelicRosewoodLute(), RelicType.SHARED);
            BaseMod.addRelic(new RelicTuningFork(), RelicType.SHARED);
            UnlockTracker.markRelicAsSeen(RelicFingerTrap.ID);
            UnlockTracker.markRelicAsSeen(RelicRosewoodLute.ID);
            UnlockTracker.markRelicAsSeen(RelicTuningFork.ID);
        }
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(ID)
            .packageFilter(ReliquaryCard.class)
            .setDefaultSeen(false)
            .cards();
    }

    @Override
    public void receiveEditKeywords() {
        String path;
        if (Settings.language == Settings.GameLanguage.DEU) {
            path = "reliquaryAssets/localization/deu/KeywordStrings.json";
        } else if (Settings.language == Settings.GameLanguage.JPN) {
            path = "reliquaryAssets/localization/jpn/KeywordStrings.json";
        } else if (Settings.language == Settings.GameLanguage.KOR) {
            path = "reliquaryAssets/localization/kor/KeywordStrings.json";
        } else if (Settings.language == Settings.GameLanguage.RUS) {
            path = "reliquaryAssets/localization/rus/KeywordStrings.json";
        } else if (Settings.language == Settings.GameLanguage.SPA) {
            path = "reliquaryAssets/localization/spa/KeywordStrings.json";
        } else if (Settings.language == Settings.GameLanguage.ZHS) {
            path = "reliquaryAssets/localization/zhs/KeywordStrings.json";
        } else {
            path = "reliquaryAssets/localization/eng/KeywordStrings.json";
        }

        Gson gson = new Gson();
        String json = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditStrings() {
        if (Settings.language == Settings.GameLanguage.DEU) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/deu/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/deu/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/deu/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/deu/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/deu/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/deu/UIStrings.json");
        } else if (Settings.language == Settings.GameLanguage.JPN) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/jpn/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/jpn/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/jpn/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/jpn/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/jpn/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/jpn/UIStrings.json");
        } else if (Settings.language == Settings.GameLanguage.KOR) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/kor/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/kor/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/kor/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/kor/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/kor/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/kor/UIStrings.json");
        } else if (Settings.language == Settings.GameLanguage.RUS) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/rus/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/rus/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/rus/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/rus/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/rus/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/rus/UIStrings.json");
        } else if (Settings.language == Settings.GameLanguage.SPA) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/spa/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/spa/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/spa/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/spa/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/spa/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/spa/UIStrings.json");
        } else if (Settings.language == Settings.GameLanguage.ZHS) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/zhs/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/zhs/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/zhs/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/zhs/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/zhs/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/zhs/UIStrings.json");
        } else {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/eng/CardStrings.json");
            BaseMod.loadCustomStringsFile(OrbStrings.class, "reliquaryAssets/localization/eng/OrbStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/eng/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/eng/RelicStrings.json");
            BaseMod.loadCustomStringsFile(StanceStrings.class, "reliquaryAssets/localization/eng/StanceStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/eng/UIStrings.json");
        }
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(AtonementStance.SFX_ENTER_ID, "reliquaryAssets/audio/sound/atonement_enter.ogg");
        BaseMod.addAudio(AtonementStance.SFX_LOOP_ID, "reliquaryAssets/audio/sound/atonement_loop.ogg");
        BaseMod.addAudio(CookieStatics.SFX_BITE_ID, CookieStatics.SFX_BITE_PATH);
        BaseMod.addAudio(CookieStatics.SFX_EAT_ID, CookieStatics.SFX_EAT_PATH);
        BaseMod.addAudio(OrbBias.SFX_CHANNEL, "reliquaryAssets/audio/sound/orb_bias_channel.ogg");
        BaseMod.addAudio(OrbBias.SFX_EVOKE, "reliquaryAssets/audio/sound/orb_bias_evoke.ogg");
        BaseMod.addAudio(OrbData.SFX_CHANNEL, "reliquaryAssets/audio/sound/orb_data_channel.ogg");
        BaseMod.addAudio(OrbData.SFX_EVOKE, "reliquaryAssets/audio/sound/orb_data_evoke.ogg");
    }

    @Override
    public void receivePostInitialize() {
        ModPanel modPanel = new ModPanel();
        try {
            modPanel = initConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseMod.registerModBadge(new Texture("reliquaryAssets/images/badge.png"), "Reliquary", "thquinn", "A collection of relics.", modPanel);
        BaseMod.addPower(DeathWarrantPower.class, DeathWarrantPower.POWER_ID);
        BaseMod.addPower(InvincibleTurnsPower.class, InvincibleTurnsPower.POWER_ID);
        BaseMod.addPower(LesserDuplicationPower.class, LesserDuplicationPower.POWER_ID);
        BaseMod.addPower(ReduceColorlessCostPower.class, ReduceColorlessCostPower.POWER_ID);
        BaseMod.addPower(TauntPower.class, TauntPower.POWER_ID);
        BaseMod.addPower(TriumphPower.class, TriumphPower.POWER_ID);
        BaseMod.addPower(VimPower.class, VimPower.POWER_ID);
        BaseMod.addSaveField("reliquary:saveAllRemovedCards", CookieStatics.removedFromMasterDeckSavable);
    }
    ModPanel initConfig() throws IOException {
        SpireConfig config = new SpireConfig(ID, CONFIG_NAME);
        ModPanel modPanel = new ModPanel();
        ModLabeledToggleButton buttonEnableRetired = new ModLabeledToggleButton(
                "Enable Retired Relics",
                "To avoid inflating the relic pool, some relics have been retired. You can find the list on my site. (Requires restart.)",
                400, 750, Settings.CREAM_COLOR, FontHelper.charDescFont,
                config.getBool(CONFIG_USE_RETIRED_RELICS), modPanel, modLabel -> {},
                button -> {
                    config.setBool(CONFIG_USE_RETIRED_RELICS, button.enabled);
                    try {
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        modPanel.addUIElement(buttonEnableRetired);
        return modPanel;
    }

    @Override
    public void receivePostUpdate() {
        CardCookieToutSweet.postUpdateStatic();
        if (AbstractDungeon.player == null) return;
        if (AbstractDungeon.player.hasRelic(RelicRedPaperclip.ID)) ((RelicRedPaperclip)AbstractDungeon.player.getRelic(RelicRedPaperclip.ID)).postUpdate();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        RelicThinkingCap.onStartBattleStatic();
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        CardCookieToutSweet.onPlayerTurnStartPostDrawStatic();
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        RelicThinkingCap.onCardUseStatic(abstractCard);
    }
}
