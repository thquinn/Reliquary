package patches;

import cards.colorless.CardPearlescence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicGummyVitamins;

@SpirePatch(
        clz= AbstractCard.class,
        method="renderEnergy"
)
public class PatchTwinPearls {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = { "costColor" }
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