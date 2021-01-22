package patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relics.RelicShortFuse;

public class PatchShortFuse {
    @SpirePatch(
            clz = LightningOrbEvokeAction.class,
            method = "update"
    )
    public static class PatchShortFuseEvoke {
        @SpireInsertPatch(
                rloc = 3,
                localvars = {"m"}
        )
        public static void Insert(LightningOrbEvokeAction __instance, @ByRef AbstractMonster[] m) {
            Impl(m);
        }
    }

    @SpirePatch(
            clz = LightningOrbPassiveAction.class,
            method = "update"
    )
    public static class PatchShortFusePassive {
        @SpireInsertPatch(
                rloc = 3,
                localvars = {"m"}
        )
        public static void Insert(LightningOrbPassiveAction __instance, @ByRef AbstractMonster[] m) {
            Impl(m);
        }
    }

    static void Impl(AbstractMonster[] abstractMonster) {
        if (AbstractDungeon.player.hasRelic(RelicShortFuse.ID)) {
            AbstractMonster weakestMonster = null;
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDeadOrEscaped()) {
                    if (weakestMonster == null) {
                        weakestMonster = m;
                        continue;
                    }
                    if (m.currentHealth < weakestMonster.currentHealth)
                        weakestMonster = m;
                }
            }
            abstractMonster[0] = weakestMonster;
            AbstractDungeon.player.getRelic(RelicShortFuse.ID).flash();
        }
    }
}