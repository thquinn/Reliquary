package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicVitrine;

import java.util.HashSet;
import java.util.UUID;

@SpirePatch(
        clz= GetAllInBattleInstances.class,
        method="get"
)
public class PatchVitrine {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"cards"}
    )
    public static void Insert(UUID uuid, HashSet<AbstractCard> cards) {
        RelicVitrine vitrine = (RelicVitrine) AbstractDungeon.player.getRelic(RelicVitrine.ID);
        if (vitrine != null && vitrine.card != null && vitrine.card.uuid.equals(uuid)) {
            // Make sure UUID-based upgrades like Genetic Algorithm and Ritual Dagger upgrade the vitrined card.
            cards.add(vitrine.card);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "drawPile");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}