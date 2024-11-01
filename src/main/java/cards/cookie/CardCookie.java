package cards.cookie;

import cards.ReliquaryCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import util.TextureLoader;

public abstract class CardCookie extends ReliquaryCard {
    static TextureAtlas.AtlasRegion SILHOUETTE_ATTACK, SILHOUETTE_ATTACK_BITE;
    int bites;

    public CardCookie(String id,
                     String name,
                     String img,
                     int cost,
                     String rawDescription,
                     AbstractCard.CardType type,
                     AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, CookieStatics.RELIQUARY_COOKIE, CardRarity.SPECIAL, target);
        if (SILHOUETTE_ATTACK == null) {
            SILHOUETTE_ATTACK = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack.png");
            SILHOUETTE_ATTACK_BITE = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack_bite.png");
        }
        bites = -1;
    }

    @Override
    public void update() {
        int newBites = CookieBiteField.bites.get(this);
        if (newBites != bites) {
            setBackgroundTexture(CookieStatics.CARD_BACK_ATTACK_BITE_SMALL, CookieStatics.CARD_BACK_ATTACK_BITE_LARGE);
            bites = newBites;
        }
        super.update();
    }

    @Override
    public TextureAtlas.AtlasRegion getCardBgAtlas() {
        return SILHOUETTE_ATTACK_BITE;
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class CookieBiteField {
        public static SpireField<Integer> bites = new SpireField<>(() -> 0);
    }
}
