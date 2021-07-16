package orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import util.ReliquaryLogger;
import util.TextureLoader;
import vfx.DataOrbDigitEffect;
import vfx.DataOrbParticleEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public abstract class OrbDataBase extends AbstractOrb {
    public static float TAU = (float)Math.PI * 2;
    public static float DIGIT_RATE = .25f;

    private Texture img2;
    float angle1, angle2, particleAngle, digitCounter;
    ArrayList<Integer> digitOffsets;

    public OrbDataBase() {
        img = TextureLoader.getTexture("reliquaryAssets/images/orbs/data1.png");
        img2 = TextureLoader.getTexture("reliquaryAssets/images/orbs/data2.png");
        angle1 = MathUtils.random(0, 360f);
        angle2 = MathUtils.random(0, 360f);
        particleAngle = MathUtils.random(0, TAU);
    }

    public void onStartOfTurn(Color c1, Color c2) {
        float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
        OrbFlareEffect orbFlareEffect = new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING);
        try {
            Field field = AbstractGameEffect.class.getDeclaredField("color");
            field.setAccessible(true);
            field.set(orbFlareEffect, c1);
            field = OrbFlareEffect.class.getDeclaredField("color2");
            field.setAccessible(true);
            field.set(orbFlareEffect, c2);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in OrbDataBase: " + e);
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(orbFlareEffect, speedTime));
    }

    public void updateAnimation(Color particleColor, Color digitColor, boolean flip) {
        super.updateAnimation();
        angle1 += .3f;
        angle2 -= .47f;
        particleAngle += 2.39996322972865332f;
        AbstractDungeon.effectsQueue.add(new DataOrbParticleEffect(this, particleAngle, particleColor));
        digitCounter += Gdx.graphics.getDeltaTime();
        if (digitCounter > DIGIT_RATE) {
            if (digitOffsets == null || digitOffsets.size() == 1) {
                ArrayList<Integer> newOffsets = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    newOffsets.add(i);
                }
                Collections.shuffle(newOffsets, MathUtils.random);
                while (digitOffsets != null && newOffsets.get(0) == digitOffsets.get(0)) {
                    Collections.shuffle(newOffsets, MathUtils.random);
                }
                if (digitOffsets != null) {
                    newOffsets.add(0, digitOffsets.get(0));
                }
                digitOffsets = newOffsets;
            }
            int offset = digitOffsets.get(0) - 2;
            digitOffsets.remove(0);
            AbstractDungeon.effectsQueue.add(new DataOrbDigitEffect(this, offset, digitColor, flip));
            digitCounter -= DIGIT_RATE;
        }
    }
    public float getBob() {
        return bobEffect.y;
    }

    public void render(SpriteBatch sb, Color c1, Color c2) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sb.setColor(c1);
        sb.draw(img, cX - 48, cY - 48 + bobEffect.y, 48, 48, 96, 96, scale, scale, angle1, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.setColor(c2);
        sb.draw(img2, cX - 48, cY - 48 + bobEffect.y, 48, 48, 96, 96, scale * .85f, scale * .85f, angle2, 0, 0, 96, 96,  false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // TODO: Opacity to block particles behind, more visual interest.
    }
}
