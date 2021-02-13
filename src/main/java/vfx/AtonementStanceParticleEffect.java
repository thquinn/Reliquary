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

public class AtonementStanceParticleEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float dur_div2;
    private float dvy;
    private float dvx;
    private float dr;
    private TextureAtlas.AtlasRegion img;

    public AtonementStanceParticleEffect() {
        duration = MathUtils.random(2f, 3f);
        img = setImg();
        scale = MathUtils.random(0.6F, 1.2F) * Settings.scale;
        dur_div2 = duration / 2.0F;
        float value = MathUtils.random(.2f, .4f);
        color = new Color(value, value, value, 0);
        vX = MathUtils.random(-25f, 25f) * Settings.scale;
        vY = MathUtils.random(-160.0F, -100.0F) * Settings.scale;
        x = AbstractDungeon.player.hb.cX + MathUtils.random(-100f, 100f) * Settings.scale;
        y = AbstractDungeon.player.hb.cY + MathUtils.random(200f, 300f) * Settings.scale;
        renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        dvx = MathUtils.random(-5f, 5f) * Settings.scale * scale;
        dvy = MathUtils.random(-30f, -20f) * Settings.scale;
        rotation = MathUtils.random(0, 360);
        dr = MathUtils.random(-1f, 1f);
    }
    private TextureAtlas.AtlasRegion setImg() {
        switch (MathUtils.random(0, 5)) {
            case 0:
                return ImageMaster.DUST_1;
            case 1:
                return ImageMaster.DUST_2;
            case 2:
                return ImageMaster.DUST_3;
            case 3:
                return ImageMaster.DUST_4;
            case 4:
                return ImageMaster.DUST_5;
        }
        return ImageMaster.DUST_6;
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.vY += Gdx.graphics.getDeltaTime() * this.dvy;
        this.vX -= Gdx.graphics.getDeltaTime() * this.dvx;
        rotation += dr;
        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.dur_div2);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F)
            this.isDone = true;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, img.packedWidth / 2.0F, img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}