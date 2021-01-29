package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.DivinityStance;
import powers.TauntPower;
import powers.TriumphPower;

import java.util.Optional;

@SpirePatch(
        clz= DivinityStance.class,
        method="atDamageGive"
)
public class PatchTriumphPower {
    public static SpireReturn<Float> Prefix(DivinityStance __instance, float damage, DamageInfo.DamageType type) {
        TriumphPower triumph = (TriumphPower) AbstractDungeon.player.getPower(TriumphPower.POWER_ID);
        if (triumph != null && type == DamageInfo.DamageType.NORMAL) {
            return SpireReturn.Return(damage * (3 + triumph.amount));
        }
        return SpireReturn.Continue();
    }
}