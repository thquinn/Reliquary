package patches;

import basemod.ReflectionHacks;
import cards.cookie.CardCookie;
import cards.cookie.CookieStatics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicStiletto;

import java.util.ArrayList;

public class PatchCookieCard {
    @SpirePatch(
            clz= CardGlowBorder.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {
                AbstractCard.class,
                Color.class
            }
    )
    public static class PatchCookieCardGlowBorder {
        public static void Postfix(CardGlowBorder __instance, AbstractCard inputCard, Color gColor) {
            if (inputCard.color == CookieStatics.RELIQUARY_COOKIE) {
                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", inputCard.getCardBgAtlas());
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderMainBorder"
    )
    public static class PatchCookieCardRenderMainBorder {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"img"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef TextureAtlas.AtlasRegion[] img) {
            if (__instance.color == CookieStatics.RELIQUARY_COOKIE) {
                img[0] = __instance.getCardBgAtlas();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}