package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.watcher.PathVictoryAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicKinkedSpring;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PatchKinkedSpring {
    @SpirePatch(
            clz= EmptyDeckShuffleAction.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchKinkedSpringMoveTriggers {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static SpireReturn Insert() {
            // Prevent relic onShuffle triggers from happening in the constructor.
            return SpireReturn.Return(null);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= EmptyDeckShuffleAction.class,
            method="update"
    )
    public static class PatchKinkedSpringNoShuffle {
        private static final Set<Class> SHUFFLE_ACTION_CLASSES = Arrays.stream(new Class[]{
                DrawCardAction.class,
                FastDrawCardAction.class,
                PlayTopCardAction.class,
                PathVictoryAction.class
        }).collect(Collectors.toSet());

        public static SpireReturn Prefix(EmptyDeckShuffleAction __instance) {
            RelicKinkedSpring kinkedSpring = (RelicKinkedSpring) AbstractDungeon.player.getRelic(RelicKinkedSpring.ID);
            if (kinkedSpring == null || __instance.amount == RelicKinkedSpring.ID.hashCode()) {
                return SpireReturn.Continue();
            }
            // Remove all actions from the queue that may have requested this shuffle — they will just try to shuffle again.
            // TODO: We could do something smart like keep track of which action types remain on the queue in successive shuffle attempts.
            AbstractDungeon.actionManager.actions.removeIf(a -> SHUFFLE_ACTION_CLASSES.contains(a.getClass()));
            __instance.isDone = true;
            kinkedSpring.flash();
            return SpireReturn.Return(null);
        }

        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert() {
            // Now that the shuffle has actually been performed, let's trigger onShuffle.
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onShuffle();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(CardGroup.class, "shuffle");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= AbstractRoom.class,
            method="endTurn"
    )
    public static class PatchKinkedSpringShuffle {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert() {
            RelicKinkedSpring kinkedSpring = (RelicKinkedSpring) AbstractDungeon.player.getRelic(RelicKinkedSpring.ID);
            if (kinkedSpring == null) {
                return;
            }
            AbstractPlayer p = AbstractDungeon.player;
            if (p.drawPile.isEmpty() && !p.discardPile.isEmpty()) {
                EmptyDeckShuffleAction shuffle = new EmptyDeckShuffleAction();
                // Set a ~secret~ value on the shuffle action telling our previous patch to let it through.
                shuffle.amount = RelicKinkedSpring.ID.hashCode();
                AbstractDungeon.actionManager.addToBottom(shuffle);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "drawPile");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}