package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class VariableMulticastAction extends AbstractGameAction {
    int multiplier, constant;
    boolean freeToPlayOnce;

    public VariableMulticastAction(int multiplier, int constant, boolean freeToPlayOnce) {
        this.multiplier = multiplier;
        this.constant = constant;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasOrb()) {
            return;
        }
        int effect = constant == -1 ? EnergyPanel.totalCount : constant;
        effect *= multiplier;
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
        }
        if (effect <= 0) {
            return;
        }
        addToTop(new EvokeOrbAction(1));
        for (int i = 0; i < effect - 1; i++) {
            addToTop(new EvokeWithoutRemovingOrbAction(1));
        }
        addToTop(new AnimateOrbAction(1));
        if (!freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }
}