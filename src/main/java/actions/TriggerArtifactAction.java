package actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class TriggerArtifactAction extends AbstractGameAction {
    AbstractCreature target;

    public TriggerArtifactAction(AbstractCreature target) {
        this.target = target;
        actionType = AbstractGameAction.ActionType.REDUCE_POWER;
    }

    @Override
    public void update() {
        addToTop(new TextAboveCreatureAction(target, ApplyPowerAction.TEXT[0]));
        duration -= Gdx.graphics.getDeltaTime();
        CardCrawlGame.sound.play("NULLIFY_SFX");
        target.getPower("Artifact").flashWithoutSound();
        target.getPower("Artifact").onSpecificTrigger();
        isDone = true;
    }
}
