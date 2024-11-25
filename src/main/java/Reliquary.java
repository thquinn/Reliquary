import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomRelic;
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
import util.ReliquaryLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        boolean useRetiredRelics = false;
        try {
            SpireConfig config = new SpireConfig(ID, CONFIG_NAME);
            useRetiredRelics = config.getBool(CONFIG_USE_RETIRED_RELICS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final boolean useRetiredRelicsFinal = useRetiredRelics;
        new AutoAdd(ID)
                .packageFilter(RelicBoilingFlask.class)
                .setDefaultSeen(false).any(ReliquaryRelic.class, (info, relic) -> {
                    if (!relic.isRetired() || useRetiredRelicsFinal) {
                        BaseMod.addRelic(relic, relic.getRelicType());
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
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
        String language = getLanguage();
        String path = String.format("reliquaryAssets/localization/%s/KeywordStrings.json", language);
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
        String language = getLanguage();
        BaseMod.loadCustomStringsFile(CardStrings.class, String.format("reliquaryAssets/localization/%s/CardStrings.json", language));
        BaseMod.loadCustomStringsFile(OrbStrings.class, String.format("reliquaryAssets/localization/%s/OrbStrings.json", language));
        BaseMod.loadCustomStringsFile(PowerStrings.class, String.format("reliquaryAssets/localization/%s/PowerStrings.json", language));
        BaseMod.loadCustomStringsFile(RelicStrings.class, String.format("reliquaryAssets/localization/%s/RelicStrings.json", language));
        BaseMod.loadCustomStringsFile(StanceStrings.class, String.format("reliquaryAssets/localization/%s/StanceStrings.json", language));
        BaseMod.loadCustomStringsFile(UIStrings.class, String.format("reliquaryAssets/localization/%s/UIStrings.json", language));
    }
    String getLanguage() {
        String language = Settings.language.name().toLowerCase();
        String[] translatedLanguages = new String[]{ "jpn", "kor", "rus", "spa", "zhs" };
        return Arrays.stream(translatedLanguages).anyMatch(language::equals) ? language : "eng";
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
