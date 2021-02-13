package stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.watcher.CannotChangeStancePower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import vfx.AtonementStanceAuraEffect;
import vfx.AtonementStanceParticleEffect;

public class AtonementStance extends AbstractStance {
    public static final String STANCE_ID = "reliquary:Atonement";
    public static final String SFX_ENTER_ID = "reliquary:STANCE_ENTER_ATONEMENT";
    public static final String SFX_LOOP_ID = "reliquary:STANCE_LOOP_ATONEMENT";
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static long sfxId = -1L;

    public AtonementStance() {
        ID = STANCE_ID;
        name = stanceString.NAME;
        updateDescription();
    }

    @Override
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if (particleTimer < 0.0F) {
                particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new AtonementStanceParticleEffect());
            }
        }

        particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (particleTimer2 < 0.0F) {
            particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new AtonementStanceAuraEffect());
        }
    }

    @Override
    public void updateDescription() {
        description = stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }
        CardCrawlGame.sound.play(SFX_ENTER_ID);
        sfxId = CardCrawlGame.sound.playAndLoop(SFX_LOOP_ID);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GRAY, true));
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CannotChangeStancePower(p)));
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    @Override
    public void onExitStance() {
        stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop(SFX_LOOP_ID, sfxId);
            sfxId = -1L;
        }
    }
}
