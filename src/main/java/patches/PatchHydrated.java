package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.MonsterStartTurnAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.EnemyTurnEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.HydratedPower;
import relics.RelicSilkGlove;

@SpirePatch(
        clz= LoseStrengthPower.class,
        method="atEndOfTurn"
)
public class PatchHydrated {
    public static SpireReturn Prefix(LoseStrengthPower __instance) {
        HydratedPower hydratedPower = (HydratedPower) __instance.owner.getPower(HydratedPower.POWER_ID);
        if (hydratedPower != null) {
            hydratedPower.flash();
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(__instance.owner, __instance.owner, HydratedPower.POWER_ID, 1));
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}