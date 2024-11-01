package cards.cookie;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class CookieStatics {
    @SpireEnum(name = "RELIQUARY_COOKIE")
    public static AbstractCard.CardColor RELIQUARY_COOKIE;
    @SpireEnum(name = "RELIQUARY_COOKIE")
    public static CardLibrary.LibraryType RELIQUARY_COOKIE_LIBRARY_COLOR;

    public static Color COLOR = new Color(0.76f, 0.65f, 0.52f, 1);
    public static final String CARD_BACK_ENERGY_LARGE = "reliquaryAssets/images/cardui/cookie/energy1024.png";
    public static final String CARD_BACK_ENERGY_SMALL = "reliquaryAssets/images/cardui/cookie/energy512.png";
    public static final String CARD_BACK_ENERGY_TEXT = "reliquaryAssets/images/cardui/cookie/energyText.png";
    public static final String CARD_BACK_ATTACK_LARGE = "reliquaryAssets/images/cardui/cookie/attack1024.png";
    public static final String CARD_BACK_ATTACK_BIT_LARGE = "reliquaryAssets/images/cardui/cookie/attack1024_bite.png";
    public static final String CARD_BACK_ATTACK_SMALL = "reliquaryAssets/images/cardui/cookie/attack512.png";
    public static final String CARD_BACK_ATTACK_BIT_SMALL = "reliquaryAssets/images/cardui/cookie/attack512_bite.png";
}
