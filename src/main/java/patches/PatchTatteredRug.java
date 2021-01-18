package patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicTatteredRug;

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
        public static void Insert(PotionPopUp __instance, AbstractPotion ___potion) {
            boolean sellMode = AbstractDungeon.getCurrRoom() instanceof ShopRoom && AbstractDungeon.player.hasRelic(RelicTatteredRug.ID);
            if (sellMode) {

                AbstractDungeon.player.gainGold(RelicTatteredRug.GetPrice(___potion));
                CardCrawlGame.sound.play("GOLD_GAIN");
                String msg = DESCRIPTIONS[MathUtils.random(3, DESCRIPTIONS.length - 1)];
                if (AbstractDungeon.isScreenUp) {
                    AbstractDungeon.shopScreen.createSpeech(msg);
                } else {
                    ShopRoom shopRoom = (ShopRoom)AbstractDungeon.getCurrRoom();
                    Merchant merchant = shopRoom.merchant;
                    AbstractDungeon.effectList.add(new SpeechBubble(merchant.hb.cX - 50.0F * Settings.xScale, merchant.hb.cY + 70.0F * Settings.yScale, 3.0F, msg, false));
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "canDiscard");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
