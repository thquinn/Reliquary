package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicCrusadersMap;

public class PatchCrusadersMap {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicCrusadersMap.ID).DESCRIPTIONS;

    @SpirePatch(
            clz= MonsterRoomElite.class,
            method="dropReward"
    )
    public static class PatchCrusadersMapEliteDrop {
        public static void Postfix() {
            RelicCrusadersMap map = (RelicCrusadersMap) AbstractDungeon.player.getRelic(RelicCrusadersMap.ID);
            if (map != null) {
                map.activate();
            }
        }
    }

    @SpirePatch(
            clz= StoreRelic.class,
            method="purchaseRelic"
    )
    public static class PatchCrusadersMapBuyMessage {
        @SpireInsertPatch(
                locator= PatchCrusadersMap.PatchCrusadersMapBuyMessage.Locator.class
        )
        public static void Insert(StoreRelic __instance, ShopScreen ___shopScreen) {
            if (__instance.relic.relicId.equals(RelicCrusadersMap.ID)) {
                ___shopScreen.createSpeech(DESCRIPTIONS[2]);
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(ShopScreen.class, "createSpeech");
                int[] matches = LineFinder.findInOrder(ctMethodToPatch, matcher);
                matches[0]++;
                return matches;
            }
        }
    }
}