package actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class TriggerArtifactAction extends AbstractGameAction {
    AbstractCreature target;

    public TriggerArtifactAction(AbstractCreature target) {
        this.target = target;
        actionType = AbstractGameAction.ActionType.REDUCE_POWER;
    }

    @Override
    public void update() {
        isDone = true;
        ArtifactPower artifact = (ArtifactPower) target.getPower(ArtifactPower.POWER_ID);
        if (artifact == null) {
            return;
        }
        addToTop(new TextAboveCreatureAction(target, ApplyPowerAction.TEXT[0]));
        duration -= Gdx.graphics.getDeltaTime();
        CardCrawlGame.sound.play("NULLIFY_SFX");
        artifact.flashWithoutSound();
        artifact.onSpecificTrigger();
    }
}
