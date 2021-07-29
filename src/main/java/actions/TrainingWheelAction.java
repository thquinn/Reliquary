package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relics.RelicTrainingWheel;

public class TrainingWheelAction extends AbstractGameAction {
    public void update() {
        isDone = true;
        RelicTrainingWheel trainingWheel = (RelicTrainingWheel) AbstractDungeon.player.getRelic(RelicTrainingWheel.ID);
        if (trainingWheel != null) {
            trainingWheel.atOpeningHand();
        }
    }
}
