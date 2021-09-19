package vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import orbs.OrbDataBase;
import util.TextureLoader;

public class DataOrbRingEffect extends AbstractGameEffect {
    private Texture img;
    private float x, y, scale, yScale, alpha, angle;
    private Color color;

    public DataOrbRingEffect(OrbDataBase orb, float angle, Color color) {
        img = TextureLoader.getTexture("reliquaryAssets/images/orbs/dataRing.png");
        x = orb.cX;
        y = orb.cY + orb.getBob();
        scale = .5f;
        yScale = .2f;
        alpha = 1;
        this.angle = angle;
        this.color = color.cpy();
    }

    public void update() {
        scale = MathUtils.lerp(scale, .9f, .2f);
        alpha -= .066f;
        if (alpha < .01f) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        color.a = alpha;
        sb.setColor(color);
        float size = 256 * scale;
        float halfSize = size / 2;
        sb.draw(img, x - halfSize, y - halfSize, halfSize, halfSize, size, size, scale, scale * yScale, angle, 0, 0, 256, 256, false, false);
    }

    public void dispose() {}
}