package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ScrapeAction;
import com.megacrit.cardcrawl.actions.defect.ScrapeFollowUpAction;
import com.megacrit.cardcrawl.actions.watcher.PathVictoryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relics.RelicKinkedSpring;
import util.ReliquaryLogger;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PatchKinkedSpring {
    @SpirePatch(
            clz= EmptyDeckShuffleAction.class,
            method="update"
    )
    public static class PatchKinkedSpringNoShuffle {
        private static final Set<Class> SHUFFLE_ACTION_CLASSES = Arrays.stream(new Class[]{
                DrawCardAction.class,
                FastDrawCardAction.class,
                PlayTopCardAction.class,
                ScrapeAction.class,
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
            ReliquaryLogger.log("Shuffle aborted by Kinked Spring.");
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz= DiscardAtEndOfTurnAction.class,
            method="update"
    )
    public static class PatchKinkedSpringShuffle {
        public static void Postfix() {
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
    }
}