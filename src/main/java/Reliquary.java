import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import cards.colorless.vapor.CardVaporAmbrosia;
import cards.colorless.vapor.CardVaporBlockPotion;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import relics.*;

@SpireInitializer
public class Reliquary implements EditCardsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditStringsSubscriber {
    public static final String ID = "reliquary";

    public static void initialize() {
        BaseMod.subscribe(new Reliquary());
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(ID)
                .packageFilter(RelicBoilingFlask.class)
                .setDefaultSeen(true);
        BaseMod.addRelic(new RelicBoilingFlask(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBookmark(), RelicType.SHARED);
        BaseMod.addRelic(new RelicBrokenClock(), RelicType.SHARED);
        BaseMod.addRelic(new RelicExpiredCoupon(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFeatherDuster(), RelicType.SHARED);
        BaseMod.addRelic(new RelicFirecrackers(), RelicType.SHARED);
        BaseMod.addRelic(new RelicIridiumChain(), RelicType.SHARED);
        BaseMod.addRelic(new RelicJackalopeHorn(), RelicType.SHARED);
        BaseMod.addRelic(new RelicKinkedSpring(), RelicType.SHARED);
        BaseMod.addRelic(new RelicKnoch(), RelicType.SHARED);
        BaseMod.addRelic(new RelicMedicineBall(), RelicType.RED);
        BaseMod.addRelic(new RelicMudwinsCradle(), RelicType.PURPLE);
        BaseMod.addRelic(new RelicPorcupineQuills(), RelicType.SHARED);
        BaseMod.addRelic(new RelicPurpleTingedLeaf(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRadioactivePellet(), RelicType.GREEN);
        BaseMod.addRelic(new RelicRoseTintedGlasses(), RelicType.SHARED);
        BaseMod.addRelic(new RelicRosewoodLute(), RelicType.SHARED);
        BaseMod.addRelic(new RelicQuartzCube(), RelicType.SHARED);
        BaseMod.addRelic(new RelicSculptingSteel(), RelicType.SHARED);
        BaseMod.addRelic(new RelicShortFuse(), RelicType.BLUE);
        BaseMod.addRelic(new RelicSilkGlove(), RelicType.SHARED);
        BaseMod.addRelic(new RelicTatteredRug(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(RelicBoilingFlask.ID);
        UnlockTracker.markRelicAsSeen(RelicBookmark.ID);
        UnlockTracker.markRelicAsSeen(RelicBrokenClock.ID);
        UnlockTracker.markRelicAsSeen(RelicExpiredCoupon.ID);
        UnlockTracker.markRelicAsSeen(RelicFeatherDuster.ID);
        UnlockTracker.markRelicAsSeen(RelicFirecrackers.ID);
        UnlockTracker.markRelicAsSeen(RelicIridiumChain.ID);
        UnlockTracker.markRelicAsSeen(RelicJackalopeHorn.ID);
        UnlockTracker.markRelicAsSeen(RelicKinkedSpring.ID);
        UnlockTracker.markRelicAsSeen(RelicKnoch.ID);
        UnlockTracker.markRelicAsSeen(RelicMedicineBall.ID);
        UnlockTracker.markRelicAsSeen(RelicMudwinsCradle.ID);
        UnlockTracker.markRelicAsSeen(RelicPorcupineQuills.ID);
        UnlockTracker.markRelicAsSeen(RelicPurpleTingedLeaf.ID);
        UnlockTracker.markRelicAsSeen(RelicRadioactivePellet.ID);
        UnlockTracker.markRelicAsSeen(RelicRoseTintedGlasses.ID);
        UnlockTracker.markRelicAsSeen(RelicRosewoodLute.ID);
        UnlockTracker.markRelicAsSeen(RelicQuartzCube.ID);
        UnlockTracker.markRelicAsSeen(RelicSculptingSteel.ID);
        UnlockTracker.markRelicAsSeen(RelicShortFuse.ID);
        UnlockTracker.markRelicAsSeen(RelicSilkGlove.ID);
        UnlockTracker.markRelicAsSeen(RelicTatteredRug.ID);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(ID)
            .packageFilter(CardVaporAmbrosia.class)
            .setDefaultSeen(true)
            .cards();
    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(new String[]{ "vapor" },"Vapors have minor effects derived from your Potions, Retain, and Exhaust.");
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, "reliquaryAssets/localization/eng/CardStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "reliquaryAssets/localization/eng/PowerStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "reliquaryAssets/localization/eng/RelicStrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "reliquaryAssets/localization/eng/UIStrings.json");
    }
}
