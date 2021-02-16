package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AtonementStanceAuraEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private TextureAtlas.AtlasRegion img;
    public boolean switcher = true;

    public AtonementStanceAuraEffect() {
        img = ImageMaster.EXHAUST_L;
        duration = 2.0F;
        scale = MathUtils.random(2.7F, 2.5F) * Settings.scale;

        float value = MathUtils.random(.3f, .5f);
        color = new Color(value, value, value, 0);

        x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 16.0F, AbstractDungeon.player.hb.width / 16.0F);
        y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 16.0F, AbstractDungeon.player.hb.height / 12.0F);
        x -= img.packedWidth / 2.0F;
        y -= img.packedHeight / 2.0F;
        switcher = !switcher;
        rotation = MathUtils.random(360.0F);
        if (switcher) {
            renderBehind = true;
            vY = MathUtils.random(0.0F, 40.0F);
        } else {
            renderBehind = false;
            vY = MathUtils.random(0.0F, -40.0F);
        }

    }

    @Override
    public void update() {
        if (duration > 1.0F) {
            color.a = Interpolation.fade.apply(0.3F, 0.0F, duration - 1.0F);
        } else {
            color.a = Interpolation.fade.apply(0.0F, 0.3F, duration);
        }

        rotation += Gdx.graphics.getDeltaTime() * vY;
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(770, 771);
        sb.draw(img, x, y, img.packedWidth / 2.0F, img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose() {
    }
}