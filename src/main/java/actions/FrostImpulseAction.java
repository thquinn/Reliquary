package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;

public class FrostImpulseAction extends AbstractGameAction {
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < p.orbs.size(); i++) {
            AbstractOrb o = p.orbs.get(i);
            if (o instanceof Frost) {
                o.onStartOfTurn();
                o.onEndOfTurn();
                if (p.hasRelic(GoldPlatedCables.ID) && i == 0) {
                    o.onStartOfTurn();
                    o.onEndOfTurn();
                }
            }
        }
        isDone = true;
    }
}