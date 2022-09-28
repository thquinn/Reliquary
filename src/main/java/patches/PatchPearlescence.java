package patches;

import cards.colorless.CardPearlescence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicGummyVitamins;
import sun.security.provider.ConfigFile;
import util.ReliquaryLogger;

import java.lang.reflect.Method;

public class PatchPearlescence {
    @SpirePatch(
            clz= AbstractCard.class,
            method="renderEnergy"
    )
    public static class PatchPearlescenceCostColor {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"costColor"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch _, Color ___ENERGY_COST_RESTRICTED_COLOR, @ByRef Color[] costColor) {
            if (__instance.price == CardPearlescence.PATCH_PRICE && AbstractDungeon.player != null && !__instance.hasEnoughEnergy()) {
                costColor[0] = ___ENERGY_COST_RESTRICTED_COLOR;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "transparency");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= UseCardAction.class,
            method="update"
    )
    public static class PatchPearlescencePurge {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(UseCardAction __instance, AbstractCard ___targetCard) {
            // we can't use purgeOnUse since that messes with duplication effects
            if (___targetCard instanceof CardPearlescence) {
                AbstractDungeon.actionManager.addToTop(new ShowCardAndPoofAction(___targetCard));
                __instance.isDone = true;
                AbstractDungeon.player.cardInUse = null;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "purgeOnUse");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= SingleCardViewPopup.class,
            method="render"
    )
    public static class PatchPearlescencePopup {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (___card instanceof CardPearlescence) {
                ___card.current_x = Settings.WIDTH / 2f;
                ___card.current_y = Settings.HEIGHT / 2f;
                ___card.drawScale = 2f;
                ___card.render(sb);
                try {
                    Method method = SingleCardViewPopup.class.getDeclaredMethod("renderArrows", SpriteBatch.class);
                    method.setAccessible(true);
                    method.invoke(__instance, sb);
                    method = SingleCardViewPopup.class.getDeclaredMethod("renderTips", SpriteBatch.class);
                    method.setAccessible(true);
                    method.invoke(__instance, sb);
                } catch (Exception e) {
                    ReliquaryLogger.error("reflection failed in PatchPearlescencePopup: " + e);
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderCardBack");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}