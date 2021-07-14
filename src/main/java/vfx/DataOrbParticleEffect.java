package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import orbs.OrbData;
import util.TextureLoader;

public class DataOrbParticleEffect extends AbstractGameEffect {
    private static float SPAWN_DISTANCE = 60;
    private Texture img;
    private OrbData orb;
    private float cX, cY, x, y, t, scale, alpha, angle;

    public DataOrbParticleEffect(OrbData orb, float angle) {
        img = TextureLoader.getTexture("reliquaryAssets/images/orbs/dataDiamond.png");
        this.orb = orb;
        cX = orb.cX;
        cY = orb.cY + orb.getBob();
        x = cX + (float)Math.cos(angle) * SPAWN_DISTANCE * Settings.scale;
        y = cY + (float)Math.sin(angle) * SPAWN_DISTANCE * Settings.scale;
        t = .6f;
        scale = .1f;
        alpha = 0;
        this.angle = (float)Math.toDegrees(angle);
        renderBehind = true;
    }

    public void update() {
        if (orb != null) {
            x += (orb.cX - cX) * .85f;
            y += (orb.cY + orb.getBob() - cY) * .85f;
            cX = orb.cX;
            cY = orb.cY + orb.getBob();
        }
        t += .06f;
        x += (cX - x) * t * Gdx.graphics.getDeltaTime();
        y += (cY - y) * t * Gdx.graphics.getDeltaTime();
        scale += .6f * Gdx.graphics.getDeltaTime();
        scale = Math.min(scale, .4f);
        alpha = Math.min(1, alpha + 1.2f * Gdx.graphics.getDeltaTime());
        if (Math.abs(x - cX) < 3) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(0.66f, 0.66f, 0.66f, alpha));
        float size = 96 * scale;
        float halfSize = size / 2;
        sb.draw(img, x - halfSize, y - halfSize, halfSize, halfSize, size, size, scale, scale, angle, 0, 0, 96, 96,  false, false);
    }

    public void dispose() {}
}