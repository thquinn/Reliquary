package patches;

import actions.TrainingWheelAction;
import com.badlogic.gdx.Game;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.green.DaggerThrow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicShortFuse;
import relics.RelicTrainingWheel;

@SpirePatch(
        clz= AbstractRoom.class,
        method="update"
)
@SpirePatch(
        clz= GameActionManager.class,
        method="getNextAction"
)
public class PatchTrainingWheel {
    @SpireInsertPatch(
            locator = PatchTrainingWheel.Locator.class
    )
    public static void Insert() {
        if (AbstractDungeon.player.hasRelic(RelicTrainingWheel.ID)) {
            AbstractDungeon.actionManager.addToBottom(new TrainingWheelAction());
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.NewExprMatcher(EnableEndTurnButtonAction.class);
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}