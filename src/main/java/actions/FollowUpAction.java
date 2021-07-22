package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public abstract class FollowUpAction extends AbstractGameAction {
    public abstract void followUp(AbstractGameAction action);
}
