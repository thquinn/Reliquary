package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import util.TextureLoader;

public class CardModShortStraw extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModShortStraw";

    private Texture img;
    private TextureRegion region;

    public CardModShortStraw() {
        img = TextureLoader.getTexture("reliquaryAssets/images/relics/shortStraw.png");
        region = new TextureRegion(img);
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        Color color = Color.WHITE.cpy();
        color.a = card.transparency;
        sb.setColor(color);
        Vector2 vec = new Vector2(0, 275);
        vec.scl(card.drawScale * Settings.scale);
        vec.rotate(card.angle);
        sb.draw(region,
                card.hb.cX + vec.x - region.getRegionWidth() / 2f,
                card.hb.cY + vec.y - region.getRegionHeight() / 2f,
                (float) region.getRegionWidth() / 2.0F,
                (float) region.getRegionHeight() / 2.0F,
                (float) region.getRegionWidth(),
                (float) region.getRegionHeight(),
                card.drawScale * 1.5f,
                card.drawScale * 1.5f,
                card.angle);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModShortStraw();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
