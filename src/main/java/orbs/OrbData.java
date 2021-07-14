package orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import powers.LoseFocusPower;
import util.ReliquaryLogger;
import util.TextureLoader;
import vfx.DataOrbDigitEffect;
import vfx.DataOrbParticleEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class OrbData extends AbstractOrb {
    public static float TAU = (float)Math.PI * 2;
    public static float DIGIT_RATE = .25f;

    public static String ORB_ID = "reliquary:Data";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;
    static final int PASSIVE_AMOUNT = 2;
    static final int EVOKE_AMOUNT = 5;
    private Texture img2;
    float angle1, angle2, particleAngle, digitCounter;
    ArrayList<Integer> digitOffsets;

    public OrbData() {
        this.ID = ORB_ID;
        this.name = NAME;
        updateDescription();
        img = TextureLoader.getTexture("reliquaryAssets/images/orbs/data1.png");
        img2 = TextureLoader.getTexture("reliquaryAssets/images/orbs/data2.png");
        angle1 = MathUtils.random(0, 360f);
        angle2 = MathUtils.random(0, 360f);
        particleAngle = MathUtils.random(0, TAU);
    }

    @Override
    public void onStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new LoseFocusPower(p, PASSIVE_AMOUNT)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, PASSIVE_AMOUNT)));
        float speedTime = 0.2F / p.orbs.size();
        OrbFlareEffect orbFlareEffect = new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING);
        try {
            Field field = OrbFlareEffect.class.getDeclaredField("color");
            field.setAccessible(true);
            field.set(orbFlareEffect, Color.LIME);
            field = OrbFlareEffect.class.getDeclaredField("color2");
            field.setAccessible(true);
            field.set(orbFlareEffect, Color.GREEN);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in SoulObtainToSideboard: " + e);
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(orbFlareEffect, speedTime));
    }

    @Override
    public void onEvoke() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new LoseFocusPower(p, EVOKE_AMOUNT)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, EVOKE_AMOUNT)));
        // TODO: Evoke animation.
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTION[0] + passiveAmount + DESCRIPTION[1] + evokeAmount + DESCRIPTION[2];
    }

    @Override
    public void applyFocus() {
        passiveAmount = PASSIVE_AMOUNT;
        evokeAmount = EVOKE_AMOUNT;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle1 += .3f;
        angle2 -= .47f;
        particleAngle += 2.39996322972865332f;
        AbstractDungeon.effectsQueue.add(new DataOrbParticleEffect(this, particleAngle));
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
            AbstractDungeon.effectsQueue.add(new DataOrbDigitEffect(this, offset));
            digitCounter -= DIGIT_RATE;
        }
    }
    public float getBob() {
        return bobEffect.y;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sb.setColor(new Color(.3f, 1, .3f, .75f));
        sb.draw(img, cX - 48, cY - 48 + bobEffect.y, 48, 48, 96, 96, scale, scale, angle1, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.setColor(new Color(.35f, 1, .35f, .66f));
        sb.draw(img2, cX - 48, cY - 48 + bobEffect.y, 48, 48, 96, 96, scale * .85f, scale * .85f, angle2, 0, 0, 96, 96,  false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new OrbData();
    }
}
