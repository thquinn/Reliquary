package patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import cardmods.CardModBouncer;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import relics.RelicBouncer;

@SpirePatch(
    cls="com.megacrit.cardcrawl.cards.AbstractCard",
    method=SpirePatch.CLASS
)
public class PatchBouncer {
    public static SpireField<Boolean> inBouncer = new SpireField<>(() -> false);

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.cards.AbstractCard",
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            inBouncer.set(__result, inBouncer.get(__instance));
            return __result;
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="bottledCardUpgradeCheck"
    )
    public static class BottledCardUpgradeCheck {
        public static void Postfix(AbstractPlayer __instance, AbstractCard c) {
            if (inBouncer.get(c) && __instance.hasRelic(RelicBouncer.ID))
                ((RelicBouncer) __instance.getRelic(RelicBouncer.ID)).setDescriptionAfterLoading();
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method="triggerOnManualDiscard"
    )
    public static class BouncerDiscard {
        public static void Postfix(AbstractCard __instance) {
            for (AbstractCardModifier cardMod : CardModifierManager.getModifiers(__instance, CardModBouncer.ID)) {
                ((CardModBouncer) cardMod).onDiscard(__instance);
            }
        }
    }
}
