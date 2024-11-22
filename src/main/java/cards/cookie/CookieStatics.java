package cards.cookie;

import basemod.abstracts.CustomSavableRaw;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import util.TextureLoader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CookieStatics {
    @SpireEnum(name = "RELIQUARY_COOKIE")
    public static AbstractCard.CardColor RELIQUARY_COOKIE;
    @SpireEnum(name = "RELIQUARY_COOKIE")
    public static CardLibrary.LibraryType RELIQUARY_COOKIE_LIBRARY_COLOR;

    public static ArrayList<AbstractCard> allCardsRemovedFromMasterDeck = new ArrayList<>();
    public static CustomSavableRaw removedFromMasterDeckSavable = new CustomSavableRaw() {
        Gson saveFileGson = new Gson();

        @Override
        public JsonElement onSaveRaw() {
            return saveFileGson.toJsonTree(allCardsRemovedFromMasterDeck.stream().map(c -> new CardSave(c.cardID, c.timesUpgraded, c.misc)).collect(Collectors.toList()));
        }

        @Override
        public void onLoadRaw(JsonElement jsonElement) {
            Type listType = new TypeToken<List<CardSave>>(){}.getType();
            List<CardSave> cardSaves = saveFileGson.fromJson(jsonElement, listType);
            allCardsRemovedFromMasterDeck = new ArrayList<>();
            for (CardSave cardSave : cardSaves) {
                allCardsRemovedFromMasterDeck.add(CardLibrary.getCopy(cardSave.id, cardSave.upgrades, cardSave.misc));
            }
        }
    };

    public static Color COLOR = new Color(0.76f, 0.65f, 0.52f, 1);
    public static final String CARD_BACK_ENERGY_LARGE = "reliquaryAssets/images/cardui/cookie/energy1024.png";
    public static final String CARD_BACK_ENERGY_SMALL = "reliquaryAssets/images/cardui/cookie/energy512.png";
    public static final String CARD_BACK_ENERGY_TEXT = "reliquaryAssets/images/cardui/cookie/energyText.png";
    public static final String[][] CARD_BACKS_ATTACK_LARGE = {
            { "reliquaryAssets/images/cardui/cookie/attack1024.png", "reliquaryAssets/images/cardui/cookie/attack1024_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/attack1024_bite.png", "reliquaryAssets/images/cardui/cookie/attack1024_bite_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/attack1024_bite2.png", "reliquaryAssets/images/cardui/cookie/attack1024_bite2_wrap.png" },
    };
    public static final String[][] CARD_BACKS_ATTACK_SMALL = {
            { "reliquaryAssets/images/cardui/cookie/attack512.png", "reliquaryAssets/images/cardui/cookie/attack512_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/attack512_bite.png", "reliquaryAssets/images/cardui/cookie/attack512_bite_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/attack512_bite2.png", "reliquaryAssets/images/cardui/cookie/attack512_bite2_wrap.png" },
    };
    public static final String[][] CARD_BACKS_POWER_LARGE = {
            { "reliquaryAssets/images/cardui/cookie/power1024.png", "reliquaryAssets/images/cardui/cookie/power1024_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/power1024_bite.png", "reliquaryAssets/images/cardui/cookie/power1024_bite_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/power1024_bite2.png", "reliquaryAssets/images/cardui/cookie/power1024_bite2_wrap.png" },
    };
    public static final String[][] CARD_BACKS_POWER_SMALL = {
            { "reliquaryAssets/images/cardui/cookie/power512.png", "reliquaryAssets/images/cardui/cookie/power512_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/power512_bite.png", "reliquaryAssets/images/cardui/cookie/power512_bite_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/power512_bite2.png", "reliquaryAssets/images/cardui/cookie/power512_bite2_wrap.png" },
    };
    public static final String[][] CARD_BACKS_SKILL_LARGE = {
            { "reliquaryAssets/images/cardui/cookie/skill1024.png", "reliquaryAssets/images/cardui/cookie/skill1024_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/skill1024_bite.png", "reliquaryAssets/images/cardui/cookie/skill1024_bite_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/skill1024_bite2.png", "reliquaryAssets/images/cardui/cookie/skill1024_bite2_wrap.png" },
    };
    public static final String[][] CARD_BACKS_SKILL_SMALL = {
            { "reliquaryAssets/images/cardui/cookie/skill512.png", "reliquaryAssets/images/cardui/cookie/skill512_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/skill512_bite.png", "reliquaryAssets/images/cardui/cookie/skill512_bite_wrap.png" },
            { "reliquaryAssets/images/cardui/cookie/skill512_bite2.png", "reliquaryAssets/images/cardui/cookie/skill512_bite2_wrap.png" },
    };

    public static final String SFX_BITE_ID = "reliquary:SFX_COOKIE_BITE";
    public static final String SFX_BITE_PATH = "reliquaryAssets/audio/sound/cookie_bite.ogg";
    public static final String SFX_EAT_ID = "reliquary:SFX_COOKIE_EAT";
    public static final String SFX_EAT_PATH = "reliquaryAssets/audio/sound/cookie_eat.ogg";
}
