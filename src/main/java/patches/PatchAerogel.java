package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relics.RelicAerogel;

public class PatchAerogel {
    @SpirePatch(
            cls="com.megacrit.cardcrawl.cards.AbstractCard",
            method=SpirePatch.CLASS
    )
    public static class PatchAerogelField {
        public static SpireField<Boolean> inAerogel = new SpireField<>(() -> false);

        @SpirePatch(
                cls = "com.megacrit.cardcrawl.cards.AbstractCard",
                method = "makeStatEquivalentCopy"
        )
        public static class MakeStatEquivalentCopy {
            public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
                inAerogel.set(__result, inAerogel.get(__instance));
                return __result;
            }
        }
    }

    @SpirePatch(
            clz= EmptyDeckShuffleAction.class,
            method="update"
    )
    public static class PatchAerogelShuffle {
        public static void Postfix(EmptyDeckShuffleAction __instance) {
            if (!__instance.isDone) {
                return;
            }
            RelicAerogel aerogel = (RelicAerogel) AbstractDungeon.player.getRelic(RelicAerogel.ID);
            if (aerogel != null) {
                aerogel.afterShuffle();
            }
        }
    }
}
