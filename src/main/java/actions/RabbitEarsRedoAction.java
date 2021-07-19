package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.List;

public class RabbitEarsRedoAction extends AbstractGameAction {
    public RabbitEarsRedoAction() {
    }

    public void update() {
        isDone = true;
        if (!AbstractDungeon.player.hasOrb()) {
            return;
        }
        AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
        addToTop(new ChannelAction(orb, false));
        addToTop(new EvokeOrbAction(1));
        List<AbstractGameAction> actions = AbstractDungeon.actionManager.actions;
        int numActions = actions.size();
        orb.onStartOfTurn();
        orb.onEndOfTurn();
        // Move the passive actions to the front of the queue so they happen immediately.
        int numShifts = actions.size() - numActions;
        for (int i = 0; i < numShifts; i++) {
            AbstractGameAction action = actions.get(actions.size() - 1);
            actions.remove(actions.size() - 1);
            actions.add(0, action);
        }
    }
}
