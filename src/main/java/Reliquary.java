import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.colorless.vapor.CardVaporAmbrosia;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import powers.*;
import relics.*;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class Reliquary implements EditCardsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostInitializeSubscriber, PostUpdateSubscriber {
    public static final String ID = "reliquary";

    public static void initialize() {
        BaseMod.subscribe(new Reliquary());
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(ID)
                .packageFilter(RelicBoilingFlask.class)
                .setDefaultSeen(true);
        BaseMod.addRelic(new RelicAerogel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBallBearing(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBoilingFlask(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBookmark(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBoomerang(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBrokenClock(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBuckler(), RelicType.SHARED);
        BaseMod.addRelic(new RelicConveyor(), RelicType.BLUE);
        BaseMod.addRelic(new RelicCraggleroot(), RelicType.SHARED);
        BaseMod.addRelic(new RelicCrutches(), RelicType.RED);
        BaseMod.addRelic(new RelicDhvaja(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicEmber(), RelicType.SHARED);
        BaseMod.addRelic(new RelicExpiredCoupon(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFeatherDuster(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFerryPass(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFirecrackers(), RelicType.SHARED);
        BaseMod.addRelic(new RelicHotPoker(), RelicType.SHARED);
        BaseMod.addRelic(new RelicIridiumChain(), RelicType.SHARED);
        BaseMod.addRelic(new RelicIvoryTrinket(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicJackalopeAntler(), RelicType.SHARED);
        BaseMod.addRelic(new RelicKillPill(), RelicType.GREEN);
        BaseMod.addRelic(new RelicKinkedSpring(), RelicType.SHARED);
        BaseMod.addRelic(new RelicKnoch(), RelicType.SHARED);
        BaseMod.addRelic(new RelicLoveEmittingDiode(), RelicType.BLUE);
        BaseMod.addRelic(new RelicMedicineBall(), RelicType.RED);
        BaseMod.addRelic(new RelicMudwinsCradle(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicNewFriend(), RelicType.SHARED);
        BaseMod.addRelic(new RelicOuijaBoard(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPartyBalloon(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPorcupineQuills(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPrincelyHelmet(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPurpleTingedLeaf(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPushpin(), RelicType.GREEN);
        BaseMod.addRelic(new RelicRadioactivePellet(), RelicType.GREEN);
        BaseMod.addRelic(new RelicRedCape(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRedPaperclip(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRoseTintedGlasses(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRosewoodLute(), RelicType.SHARED);
        BaseMod.addRelic(new RelicQuartzCube(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSculptingSteel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSharkskinSheath(), RelicType.SHARED);
        BaseMod.addRelic(new RelicShortFuse(), RelicType.BLUE);
        BaseMod.addRelic(new RelicSideboard(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSilkGlove(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSolitaire(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicStiletto(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTamtam(), RelicType.RED);
        BaseMod.addRelic(new RelicTatteredRug(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTridentHead(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTuningFork(), RelicType.SHARED);
        BaseMod.addRelic(new RelicWeakTea(), RelicType.SHARED);
        BaseMod.addRelic(new RelicWritOfMandamus(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(RelicAerogel.ID);
        UnlockTracker.markRelicAsSeen(RelicBallBearing.ID);
        UnlockTracker.markRelicAsSeen(RelicBoilingFlask.ID);
        UnlockTracker.markRelicAsSeen(RelicBookmark.ID);
        UnlockTracker.markRelicAsSeen(RelicBoomerang.ID);
        UnlockTracker.markRelicAsSeen(RelicBrokenClock.ID);
        UnlockTracker.markRelicAsSeen(RelicBuckler.ID);
        UnlockTracker.markRelicAsSeen(RelicConveyor.ID);
        UnlockTracker.markRelicAsSeen(RelicCraggleroot.ID);
        UnlockTracker.markRelicAsSeen(RelicCrutches.ID);
        UnlockTracker.markRelicAsSeen(RelicDhvaja.ID);
        UnlockTracker.markRelicAsSeen(RelicEmber.ID);
        UnlockTracker.markRelicAsSeen(RelicExpiredCoupon.ID);
        UnlockTracker.markRelicAsSeen(RelicFeatherDuster.ID);
        UnlockTracker.markRelicAsSeen(RelicFerryPass.ID);
        UnlockTracker.markRelicAsSeen(RelicFirecrackers.ID);
        UnlockTracker.markRelicAsSeen(RelicHotPoker.ID);
        UnlockTracker.markRelicAsSeen(RelicIridiumChain.ID);
        UnlockTracker.markRelicAsSeen(RelicIvoryTrinket.ID);
        UnlockTracker.markRelicAsSeen(RelicJackalopeAntler.ID);
        UnlockTracker.markRelicAsSeen(RelicKillPill.ID);
        UnlockTracker.markRelicAsSeen(RelicKinkedSpring.ID);
        UnlockTracker.markRelicAsSeen(RelicKnoch.ID);
        UnlockTracker.markRelicAsSeen(RelicLoveEmittingDiode.ID);
        UnlockTracker.markRelicAsSeen(RelicMedicineBall.ID);
        UnlockTracker.markRelicAsSeen(RelicMudwinsCradle.ID);
        UnlockTracker.markRelicAsSeen(RelicNewFriend.ID);
        UnlockTracker.markRelicAsSeen(RelicOuijaBoard.ID);
        UnlockTracker.markRelicAsSeen(RelicPartyBalloon.ID);
        UnlockTracker.markRelicAsSeen(RelicPorcupineQuills.ID);
        UnlockTracker.markRelicAsSeen(RelicPrincelyHelmet.ID);
        UnlockTracker.markRelicAsSeen(RelicPurpleTingedLeaf.ID);
        UnlockTracker.markRelicAsSeen(RelicPushpin.ID);
        UnlockTracker.markRelicAsSeen(RelicRadioactivePellet.ID);
        UnlockTracker.markRelicAsSeen(RelicRedCape.ID);
        UnlockTracker.markRelicAsSeen(RelicRedPaperclip.ID);
        UnlockTracker.markRelicAsSeen(RelicRoseTintedGlasses.ID);
        UnlockTracker.markRelicAsSeen(RelicRosewoodLute.ID);
        UnlockTracker.markRelicAsSeen(RelicQuartzCube.ID);
        UnlockTracker.markRelicAsSeen(RelicSculptingSteel.ID);
        UnlockTracker.markRelicAsSeen(RelicSharkskinSheath.ID);
        UnlockTracker.markRelicAsSeen(RelicShortFuse.ID);
        UnlockTracker.markRelicAsSeen(RelicSideboard.ID);
        UnlockTracker.markRelicAsSeen(RelicSilkGlove.ID);
        UnlockTracker.markRelicAsSeen(RelicStiletto.ID);
        UnlockTracker.markRelicAsSeen(RelicSolitaire.ID);
        UnlockTracker.markRelicAsSeen(RelicTamtam.ID);
        UnlockTracker.markRelicAsSeen(RelicTatteredRug.ID);
        UnlockTracker.markRelicAsSeen(RelicTridentHead.ID);
        UnlockTracker.markRelicAsSeen(RelicTuningFork.ID);
        UnlockTracker.markRelicAsSeen(RelicWeakTea.ID);
        UnlockTracker.markRelicAsSeen(RelicWritOfMandamus.ID);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(ID)
            .packageFilter(CardVaporAmbrosia.class)
            .setDefaultSeen(false)
            .cards();
    }

    @Override
    public void receiveEditKeywords() {
        String path;
        if (Settings.language == Settings.GameLanguage.RUS) {
            path = "reliquaryAssets/localization/rus/KeywordStrings.json";
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
        if (Settings.language == Settings.GameLanguage.RUS) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/rus/CardStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/rus/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/rus/RelicStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/rus/UIStrings.json");
        } else if (Settings.language == Settings.GameLanguage.ZHS) {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/zhs/CardStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/zhs/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/zhs/RelicStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/zhs/UIStrings.json");
        } else {
            BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/eng/CardStrings.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/eng/PowerStrings.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/eng/RelicStrings.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/eng/UIStrings.json");
        }
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.registerModBadge(new Texture("reliquaryAssets/images/badge.png"), "Reliquary", "thquinn", "A collection of relics.", new ModPanel());
        BaseMod.addPower(InvincibleTurnsPower.class, InvincibleTurnsPower.POWER_ID);
        BaseMod.addPower(LesserDuplicationPower.class, LesserDuplicationPower.POWER_ID);
        BaseMod.addPower(ReduceColorlessCostPower.class, ReduceColorlessCostPower.POWER_ID);
        BaseMod.addPower(TauntPower.class, TauntPower.POWER_ID);
        BaseMod.addPower(TriumphPower.class, TriumphPower.POWER_ID);
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player == null) return;
        if (AbstractDungeon.player.hasRelic(RelicFerryPass.ID)) ((RelicFerryPass)AbstractDungeon.player.getRelic(RelicFerryPass.ID)).postUpdate();
        if (AbstractDungeon.player.hasRelic(RelicRedPaperclip.ID)) ((RelicRedPaperclip)AbstractDungeon.player.getRelic(RelicRedPaperclip.ID)).postUpdate();
    }
}
