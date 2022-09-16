package cards.colorless.vapor;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import relics.RelicBoilingFlask;

public abstract class CardVapor extends CustomCard {
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicBoilingFlask.ID).DESCRIPTIONS;

    boolean wide = false;

    public CardVapor(String id,
                     String name,
                     String img,
                     int cost,
                     String rawDescription,
                     AbstractCard.CardType type,
                     AbstractCard.CardColor color,
                     AbstractCard.CardRarity rarity,
                     AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public AbstractCard makeCopy() {
        try {
            CardVapor copy = getClass().newInstance();
            if (wide) {
                copy.widen(false);
            }
            return copy;
        } catch (Exception e) {
        }
        return null;
    }

    public void widen(boolean changeName) {
        wide = true;
        if (changeName) {
            name = DESCRIPTIONS[1] + name + DESCRIPTIONS[2];
        }
        TextureData textureData = portrait.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap srcPixmap = textureData.consumePixmap();
        int width = srcPixmap.getWidth();
        float stretchedWidth = width * 2;
        int height = srcPixmap.getHeight();
        Pixmap pixmap = new Pixmap(width, height, srcPixmap.getFormat());
        pixmap.drawPixmap(srcPixmap, 0, 0, width, height, -Math.round((stretchedWidth - width) / 2), 0, Math.round(stretchedWidth), height);
        if (type != CardType.SKILL) {
            // Mask the widened image to the original. Surely there's a better (non-pixelwise) way to do this...
            pixmap.setBlending(Pixmap.Blending.None);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int color = pixmap.getPixel(x, y);
                    color &= 0xFFFFFF00;
                    color |= (srcPixmap.getPixel(x, y) & 0x000000FF);
                    pixmap.drawPixel(x, y, color);
                }
            }
            pixmap.setBlending(Pixmap.Blending.SourceOver);
        }
        portraitImg = new Texture(pixmap);
        int tw = portraitImg.getWidth();
        int th = portraitImg.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(portraitImg, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "portrait", cardImg);
    }
}
