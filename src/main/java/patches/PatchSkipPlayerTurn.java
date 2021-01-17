package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MonsterStartTurnAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.EnemyTurnEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(
        clz=AbstractRoom.class,
        method=SpirePatch.CLASS
)
public class PatchSkipPlayerTurn
{
    public static SpireField<Boolean> skipPlayerTurn = new SpireField<>(() -> false);

    @SpirePatch(
            clz= GameActionManager.class,
            method="getNextAction"
    )
    private static class PatchSkipPlayerTurnInternal {
        @SpireInsertPatch(locator=Locator.class)
        public static SpireReturn Insert(GameActionManager __instance) {
            if (__instance.monsterQueue.isEmpty() && PatchSkipPlayerTurn.skipPlayerTurn.get(AbstractDungeon.getCurrRoom())) {
                AbstractDungeon.getCurrRoom().monsters.applyEndOfTurnPowers();
                AbstractDungeon.getCurrRoom().monsters.queueMonsters();
                AbstractDungeon.getMonsters().showIntent();
                AbstractDungeon.topLevelEffects.add(new EnemyTurnEffect());
                AbstractDungeon.actionManager.addToBottom(new MonsterStartTurnAction());
                PatchSkipPlayerTurn.skipPlayerTurn.set(AbstractDungeon.getCurrRoom(), false);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "monsterQueue");
            int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
            return new int[]{ matches[matches.length - 1] };
        }
    }
}