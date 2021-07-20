package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicKnifeBlock;
import relics.RelicPartyBalloon;

import java.util.Collections;

@SpirePatch(
        clz= MakeTempCardInHandAction.class,
        method="update"
)
public class PatchKnifeBlock {
    public static void Prefix(MakeTempCardInHandAction __instance, AbstractCard ___c) {
        AbstractRelic knifeBlock = AbstractDungeon.player.getRelic(RelicKnifeBlock.ID);
        if (knifeBlock != null && ___c.cardID.equals(Shiv.ID) && __instance.amount > 0) {
            __instance.amount++;
            knifeBlock.flash();
        }
    }
}