package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import relics.RelicPurpleTingedLeaf;

@SpirePatch(
    cls="com.megacrit.cardcrawl.cards.AbstractCard",
    method=SpirePatch.CLASS
)
public class PatchPurpleTingedLeaf {
    public static SpireField<Boolean> inPurpleTingedLeaf = new SpireField<>(() -> false);

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.cards.AbstractCard",
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            inPurpleTingedLeaf.set(__result, inPurpleTingedLeaf.get(__instance));
            return __result;
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="bottledCardUpgradeCheck"
    )
    public static class BottledCardUpgradeCheck {
        public static void Postfix(AbstractPlayer __instance, AbstractCard c) {
            if (inPurpleTingedLeaf.get(c) && __instance.hasRelic(RelicPurpleTingedLeaf.ID))
                ((RelicPurpleTingedLeaf) __instance.getRelic(RelicPurpleTingedLeaf.ID)).setDescriptionAfterLoading();
        }
    }
}
