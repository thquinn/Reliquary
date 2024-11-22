package patches;

import actions.CookieCardAnimatePlayAction;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import cards.cookie.CardCookie;
import cards.cookie.CardCookieSweetNothings;
import cards.cookie.CookieStatics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicCookieJar;
import relics.RelicStiletto;
import util.TextureLoader;

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

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardBack"
    )
    public static class PatchCookieCardPopupRenderCardBack {
        public static SpireReturn Prefix(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (___card.color == CookieStatics.RELIQUARY_COOKIE) {
                CardCookie cookie = (CardCookie) ___card;
                int bites = CardCookie.CookieBiteField.bites.get(cookie);
                int timesUpgraded = cookie.timesUpgraded + (SingleCardViewPopup.isViewingUpgrade ? 1 : 0);
                TextureAtlas.AtlasRegion img = TextureLoader.asAtlasRegion(cookie.getBackgroundLargeTexturePath(cookie.type, bites, timesUpgraded));
                float x = Settings.WIDTH / 2.0F;
                float y = Settings.HEIGHT / 2.0F;
                sb.draw(img, x + img.offsetX - img.originalWidth / 2.0F, y + img.offsetY - img.originalHeight / 2.0F, img.originalWidth / 2.0F - img.offsetX, img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, Settings.scale, Settings.scale, 0.0F);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardTypeText"
    )
    public static class PatchCookieCardPopupRenderTypeText {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"card", "label"}
        )
        public static SpireReturn Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card, String label) {
            if (card.color == CookieStatics.RELIQUARY_COOKIE) {
                Color typeColor = ReflectionHacks.getPrivateInherited(card, CustomCard.class, "typeColor");
                typeColor.a = 1;
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, label, Settings.WIDTH / 2.0F + 3.0F * Settings.scale, Settings.HEIGHT / 2.0F - 40.0F * Settings.scale, typeColor);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class PatchCookieCardBiteAnimation {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
            if (c.color == CookieStatics.RELIQUARY_COOKIE) {
                AbstractDungeon.actionManager.addToBottom(new CookieCardAnimatePlayAction(c));
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz = CardGroup.class,
            method = "removeCard",
            paramtypez = {
                    AbstractCard.class,
            }
    )
    public static class PatchCookieCardSweetNothings {
        public static void Postfix(CardGroup __instance, AbstractCard card) {
            if (__instance.type == CardGroup.CardGroupType.MASTER_DECK) {
                CookieStatics.allCardsRemovedFromMasterDeck.add(card);
            }
        }
    }

    @SpirePatch(
            clz = GridCardSelectScreen.class,
            method = "update"
    )
    public static class PatchCookieCardGridShowOnly {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(GridCardSelectScreen __instance) {
            if (RelicCookieJar.previewGridOpen) {
                RelicCookieJar.previewGridOpen = false;
                AbstractDungeon.closeCurrentScreen();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(CardGroup.class, "group");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}