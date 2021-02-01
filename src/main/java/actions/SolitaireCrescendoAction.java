package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.WrathStance;

public class SolitaireCrescendoAction extends AbstractGameAction {
    public SolitaireCrescendoAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.stance.ID.equals(WrathStance.STANCE_ID)) {
            addToTop(new ApplyPowerAction(p, p, new LoseStrengthPower(p, amount), amount));
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
        } else {
            addToTop(new ChangeStanceAction(WrathStance.STANCE_ID));
        }
        isDone = true;
    }
}
