package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import orbs.OrbDataBase;
import util.TextureLoader;

public class DataOrbDigitEffect extends AbstractGameEffect {
    private static float SHIFT_PERCENT = .25f;
    private static float RATE = 4;
    private static float SCALE = .35f;
    private static float FADE_TIME = .2f;
    private static int SPAWNS = 6;
    private static float SPAWN_T = -FADE_TIME * RATE * .5f;
    private Texture img;
    private AbstractOrb orb;
    private float lastX, lastY, shiftX, shiftY;
    private float x, y, t;
    private int srcX, srcY;
    private int spawnsLeft;
    private boolean spawned;
    private Color color;

    public DataOrbDigitEffect(OrbDataBase orb, int xOffset, Color color, boolean flip) {
        this(orb,
             orb.cX + xOffset * 26 * SCALE,
             orb.cY + orb.getBob() + (flip ? -24 : 24),
             0,
             0,
             flip ? - SPAWNS : SPAWNS,
             color);
    }
    public DataOrbDigitEffect(AbstractOrb orb, float x, float y, float shiftX, float shiftY, int spawnsLeft, Color color) {
        img = TextureLoader.getTexture("reliquaryAssets/images/orbs/dataDigits.png");
        this.orb = orb;
        lastX = orb.cX;
        lastY = orb.cY;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.x = x;
        this.y = y;
        t = -FADE_TIME * RATE;
        srcX = MathUtils.random.nextInt(2) * 64;
        srcY = 0;
        this.spawnsLeft = spawnsLeft;
        this.color = color;
    }

    public void update() {
        // Move to be closer to the parent orb.
        if (orb != null) {
            shiftX += orb.cX - lastX;
            shiftY += orb.cY - lastY;
            lastX = orb.cX;
            lastY = orb.cY;
        }
        x += shiftX * SHIFT_PERCENT;
        y += shiftY * SHIFT_PERCENT;
        shiftX *= (1 - SHIFT_PERCENT);
        shiftY *= (1 - SHIFT_PERCENT);
        // Animation.
        t += Gdx.graphics.getDeltaTime() * RATE;
        if (!spawned && spawnsLeft != 0 && t >= SPAWN_T) {
            if (spawnsLeft > 0) {
                AbstractDungeon.effectsQueue.add(new DataOrbDigitEffect(orb, x, y - 26 * SCALE, shiftX, shiftY, spawnsLeft - 1, color));
            } else {
                AbstractDungeon.effectsQueue.add(new DataOrbDigitEffect(orb, x, y + 26 * SCALE, shiftX, shiftY, spawnsLeft + 1, color));
            }
            spawned = true;
        }
        if (t > 1 + FADE_TIME * RATE) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (t < 0) {
            color.a = 1 - t / -(FADE_TIME * RATE);
        } else if (t > 1) {
            color.a = 1 - (t - 1) / (FADE_TIME * RATE);
        } else {
            color.a = 1;
        }
        if (spawnsLeft == 0) {
            color.a *= .5f;
        }
        sb.setColor(color);
        float size = 64 * SCALE;
        float halfSize = size / 2;
        sb.draw(img, x - halfSize, y - halfSize, halfSize, halfSize, size, size, SCALE, SCALE, 0, srcX, srcY, 64, 64,  false, false);
    }

    public void dispose() {}
}