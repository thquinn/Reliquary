package patches;

import basemod.devcommands.relic.Relic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicQuartzCube;
import relics.RelicTatteredRug;
import util.ReliquaryLogger;

import java.util.ArrayList;

@SpirePatch(
        clz= PotionPopUp.class,
        method=SpirePatch.CLASS
)
public class PatchTatteredRug {
    private static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicTatteredRug.ID).DESCRIPTIONS;
    private static String DISCARD_TEXT = PotionPopUp.TEXT[2];

    @SpirePatch(
            clz= PotionPopUp.class,
            method="render"
    )
    public static class PatchTatteredRugRender {
        public static void Prefix(PotionPopUp __instance, SpriteBatch sb, AbstractPotion ___potion) {
            if (__instance.isHidden)
                return;
            boolean sellMode = AbstractDungeon.getCurrRoom() instanceof ShopRoom && AbstractDungeon.player.hasRelic(RelicTatteredRug.ID);
            if (sellMode) {
                PotionPopUp.TEXT[2] = DESCRIPTIONS[1] + RelicTatteredRug.GetPrice(___potion) + DESCRIPTIONS[2];
            } else {
                PotionPopUp.TEXT[2] = DISCARD_TEXT;
            }
        }
    }

    @SpirePatch(
            clz= PotionPopUp.class,
            method="updateInput"
    )
    public static class PatchTatteredRugUpdateInput {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static SpireReturn Insert(PotionPopUp __instance, AbstractPotion ___potion, Hitbox ___hbBot) {
            boolean sellMode = AbstractDungeon.getCurrRoom() instanceof ShopRoom && AbstractDungeon.player.hasRelic(RelicTatteredRug.ID);
            if (!sellMode) {
                return SpireReturn.Continue();
            }
            ShopRoom shopRoom = (ShopRoom)AbstractDungeon.getCurrRoom();
            if (RelicTatteredRug.SOLD_POTION_IDS.contains(___potion.ID)) {
                String msg = DESCRIPTIONS[MathUtils.random(11, DESCRIPTIONS.length - 1)];
                createSpeech(msg);
                CInputActionSet.select.unpress();
                ___hbBot.clicked = false;
                __instance.close();
                return SpireReturn.Return();
            }
            AbstractDungeon.player.gainGold(RelicTatteredRug.GetPrice(___potion));
            RelicTatteredRug.SOLD_POTION_IDS.add(___potion.ID);
            CardCrawlGame.sound.play("GOLD_GAIN");
            String msg = DESCRIPTIONS[MathUtils.random(3, 9)];
            createSpeech(msg);
            return SpireReturn.Continue();
        }

        private static void createSpeech(String msg) {
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.shopScreen.createSpeech(msg);
            } else {
                ShopRoom shopRoom = (ShopRoom)AbstractDungeon.getCurrRoom();
                Merchant merchant = shopRoom.merchant;
                AbstractDungeon.effectList.add(new SpeechBubble(merchant.hb.cX - 50.0F * Settings.xScale, merchant.hb.cY + 70.0F * Settings.yScale, 3.0F, msg, false));
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "canDiscard");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= ShopScreen.class,
            method="initPotions"
    )
    public static class PatchTatteredRugInitPotions {
        public static void Postfix(ShopScreen __instance, ArrayList<StorePotion> ___potions) {
            RelicTatteredRug.SOLD_POTION_IDS.clear();
            for (StorePotion p : ___potions) {
                RelicTatteredRug.SOLD_POTION_IDS.add(p.potion.ID);
            }
            ReliquaryLogger.log("Rug now contains:");
            for (String s : RelicTatteredRug.SOLD_POTION_IDS) {
                ReliquaryLogger.log(s);
            }
        }
    }

    @SpirePatch(
            clz= StorePotion.class,
            method="purchasePotion"
    )
    public static class PatchTatteredRugPurchasePotion {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static void Insert(StorePotion __instance) {
            // When The Courier refreshes the potion, make the new potion unsellable.
            RelicTatteredRug.SOLD_POTION_IDS.add(__instance.potion.ID);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "getPrice");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
