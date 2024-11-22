package actions;

import basemod.helpers.VfxBuilder;
import cards.cookie.CardCookie;
import cards.cookie.CookieStatics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class CookieCardAnimatePlayAction extends AbstractGameAction {
    static float DURATION = .25f;
    static float ANGLE_EXTENT = -20;

    public AbstractCard target;
    boolean bit;

    public CookieCardAnimatePlayAction(AbstractCard target) {
        this.target = target;
        duration = DURATION;
    }
    public boolean didGetBit() {
        return duration <= DURATION / 2;
    }

    public void update() {
        if (target.upgraded) {
            isDone = true;
            return;
        }
        tickDuration();
        boolean purging = PurgeField.purge.get(target);
        if (!purging) {
            float t = duration / DURATION;
            t *= t;
            float angleMult = (float) Math.sin(t * 2 * Math.PI);
            target.targetAngle = angleMult * ANGLE_EXTENT;
            target.angle = target.targetAngle;
        }

        // Particles, sound.
        if (!bit && didGetBit()) {
            bit = true;
            float x = target.hb.cX;
            float y = target.hb.cY;
            int bites = CardCookie.CookieBiteField.bites.get(target);
            if (bites == 1) {
                x += target.hb.width * .35;
                y += target.hb.height * .4;
            } else if (bites == 2  && !purging) {
                x -= target.hb.width * .35;
                y -= target.hb.height * .4;
            }
            for (int i = 0; i < 32; i++) {
                float angle = MathUtils.random(0, 360);
                float dAngle = Math.abs(180 - angle) / 180;
                dAngle = MathUtils.lerp(.2f, 1, dAngle);
                angle += 90;
                float velocity = 700 * dAngle;
                velocity *= MathUtils.random(.5f, 1f);
                float r = MathUtils.random(.7f, 1f);
                Color color = new Color(r, r * .4f, 0, 1);
                AbstractDungeon.effectsQueue.add(new VfxBuilder(ImageMaster.DUST_1, x, y, 1.2f)
                        .velocity(angle, velocity)
                        .gravity(20)
                        .setColor(color)
                        .setAngle(MathUtils.random(0, 360))
                        .fadeIn(0.1f)
                        .fadeOut(0.33f)
                        .build());
            }
            CardCrawlGame.sound.play(CookieStatics.SFX_BITE_ID);
            if (purging) {
                CardCrawlGame.sound.play(CookieStatics.SFX_EAT_ID);
            }
        }
    }
}
