package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class ChannelUntilFullAction extends AbstractGameAction {
    AbstractOrb orb;

    public ChannelUntilFullAction(AbstractOrb orb) {
        this.orb = orb;
    }

    public void update() {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof EmptyOrbSlot) {
                AbstractDungeon.actionManager.addToTop(new ChannelAction(orb.makeCopy()));
            }
        }
        isDone = true;
    }
}