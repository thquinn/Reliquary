package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicAluminiumFoil;
import util.ReliquaryLogger;

import java.util.Arrays;

public class PatchAluminiumFoil {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class PatchAluminiumFoilRarity {
        @SpireInsertPatch(
                locator = PatchAluminiumFoil.PatchAluminiumFoilRarity.Locator.class,
                localvars = {"rarity"}
        )
        public static void Insert(@ByRef AbstractCard.CardRarity[] rarity) {
            int foilCount = (int) AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(RelicAluminiumFoil.ID)).count();
            if (foilCount == 0) {
                return;
            }
            foilCount *= RelicAluminiumFoil.RARITY_STRENGTH;
            int[] rarities = new int[foilCount + 1];
            rarities[0] = rarityToInt(rarity[0]);
            for (int i = 0; i < foilCount; i++) {
                rarities[i + 1] = rarityToInt(AbstractDungeon.rollRarity());
            }
            if (Arrays.stream(rarities).anyMatch(r -> r == -1)) {
                return;
            }
            int max = Arrays.stream(rarities).max().getAsInt();
            rarity[0] = intToRarity(max);
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.relicId.equals(RelicAluminiumFoil.ID)) {
                    r.flash();
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "rollRarity");
                int[] match = LineFinder.findInOrder(ctMethodToPatch, matcher);
                match[0]++;
                return match;
            }
        }
        private static int rarityToInt(AbstractCard.CardRarity rarity) {
            switch (rarity) {
                case COMMON:
                    return 0;
                case UNCOMMON:
                    return 1;
                case RARE:
                    return 2;
                default:
                    ReliquaryLogger.error("Unexpected rarity from rollRarity(). Aluminium Foil will have no effect.");
                    return -1;
            }
        }
        private static AbstractCard.CardRarity intToRarity(int i) {
            switch (i) {
                case 0:
                    return AbstractCard.CardRarity.COMMON;
                case 1:
                    return AbstractCard.CardRarity.UNCOMMON;
                case 2:
                    return AbstractCard.CardRarity.RARE;
                default:
                    ReliquaryLogger.error("Unexpected int from rarityToInt().");
                    return AbstractCard.CardRarity.COMMON;
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class PatchAluminiumFoilUpgrade {
        @SpireInsertPatch(
                locator = PatchAluminiumFoil.PatchAluminiumFoilUpgrade.Locator.class,
                localvars = {"c"}
        )
        public static void Insert(AbstractCard c) {
            int foilCount = (int) AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(RelicAluminiumFoil.ID)).count();
            for (int i = 0; i < foilCount; i++) {
                if (c.canUpgrade() && AbstractDungeon.cardRng.randomBoolean(RelicAluminiumFoil.UPGRADE_STRENGTH)) {
                    c.upgrade();
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(Random.class, "randomBoolean");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}