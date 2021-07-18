package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;
import java.util.Collections;

public class MoveOrbsLeftAction extends AbstractGameAction {
    private final String orbType;

    public MoveOrbsLeftAction(String orbType) {
        this.orbType = orbType;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractOrb> orbs = p.orbs;
        boolean swap = true;
        while (swap) {
            swap = false;
            for (int i = 0; i < p.maxOrbs - 1; i++) {
                AbstractOrb two = orbs.get(i + 1);
                if (two instanceof EmptyOrbSlot) {
                    break;
                }
                AbstractOrb one = orbs.get(i);
                if (one.ID.equals(orbType) && !two.ID.equals(orbType)) {
                    Collections.swap(orbs, i, i + 1);
                    two.setSlot(i, p.maxOrbs);
                    one.setSlot(i + 1, p.maxOrbs);
                    swap = true;
                }
            }
        }
        isDone = true;
    }
}
