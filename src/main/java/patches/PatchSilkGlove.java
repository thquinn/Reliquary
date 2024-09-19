package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.EstablishmentPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicSilkGlove;

@SpirePatch(
        clz= DiscardAtEndOfTurnAction.class,
        method="update"
)
public class PatchSilkGlove {
    @SpireInsertPatch(
            locator= Locator.class
    )
    public static void Insert() {
        AbstractPlayer p = AbstractDungeon.player;
        EstablishmentPower establishmentPower = (EstablishmentPower) p.getPower(EstablishmentPower.POWER_ID);
        if (!p.hasRelic(RelicSilkGlove.ID) || p.hasRelic(RunicPyramid.ID)) {
            return;
        }
        int discards = p.hand.size();
        discards -= p.hand.group.stream().filter(c -> c.selfRetain || c.retain || c.isEthereal).count();
        if (discards == 1) {
            AbstractDungeon.actionManager.actions.removeIf(a -> a instanceof RetainCardsAction);
            for (AbstractCard c : p.hand.group) {
                if (!c.isEthereal) {
                    c.retain = true;
                    if (establishmentPower != null) {
                        // Hacky, but unfortunately Well-Laid Plans' selection resolves after Establishment, so there's
                        // no other place to put this.
                        c.modifyCostForCombat(-establishmentPower.amount);
                    }
                }
            }
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, p.getRelic(RelicSilkGlove.ID)));
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "hand");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}