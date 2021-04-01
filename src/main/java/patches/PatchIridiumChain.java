package patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import cardmods.CardModEthereal;
import cardmods.CardModIridiumCopy;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicQuartzCube;
import relics.RelicSilkGlove;
import util.ReliquaryLogger;

import javax.smartcardio.Card;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@SpirePatch(
        clz= MummifiedHand.class,
        method="onUseCard"
)
public class PatchIridiumChain {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"groupCopy"}
    )
    public static void Insert(MummifiedHand __instance, AbstractCard card, UseCardAction action, ArrayList<AbstractCard> groupCopy) {
        groupCopy.removeIf(c -> CardModifierManager.hasModifier(c, CardModIridiumCopy.ID));
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}