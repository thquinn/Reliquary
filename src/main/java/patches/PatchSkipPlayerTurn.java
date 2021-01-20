package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MonsterStartTurnAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.EnemyTurnEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class PatchSkipPlayerTurn
{
    @SpirePatch(
            clz=AbstractRoom.class,
            method=SpirePatch.CLASS
    )
    public static class SkipPlayerTurn {
        public static SpireField<Boolean> skipPlayerTurn = new SpireField<>(() -> false);

        @SpirePatch(
                clz = GameActionManager.class,
                method = "getNextAction"
        )
        private static class PatchSkipPlayerTurnInternal {
            @SpireInsertPatch(locator = Locator.class)
            public static SpireReturn Insert(GameActionManager __instance) {
                if (__instance.monsterQueue.isEmpty() && skipPlayerTurn.get(AbstractDungeon.getCurrRoom())) {
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.2f));
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        public void update() {
                            AbstractDungeon.getCurrRoom().monsters.applyEndOfTurnPowers();
                            AbstractDungeon.getCurrRoom().monsters.queueMonsters();
                            AbstractDungeon.getMonsters().showIntent();
                            AbstractDungeon.topLevelEffects.add(new EnemyTurnEffect());
                            isDone = true;
                        }
                    });
                    AbstractDungeon.actionManager.addToBottom(new MonsterStartTurnAction());
                    GameActionManager.damageReceivedThisTurn = 0;
                    skipPlayerTurn.set(AbstractDungeon.getCurrRoom(), false);
                    return SpireReturn.Return(null);
                }
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "monsterQueue");
                int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
                return new int[]{matches[matches.length - 1]};
            }
        }
    }
}