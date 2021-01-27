package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.TauntPower;

import java.util.Optional;

@SpirePatch(
        clz= AbstractCard.class,
        method="canUse"
)
public class PatchTauntPower {
    public static SpireReturn<Boolean> Prefix(AbstractCard __instance, AbstractPlayer p, AbstractMonster m) {
        if (__instance.target != AbstractCard.CardTarget.ENEMY || m == null) {
            return SpireReturn.Continue();
        }
        Optional<AbstractMonster> taunter = AbstractDungeon.getMonsters().monsters.stream().filter(mo -> mo.hasPower(TauntPower.POWER_ID)).findAny();
        if (taunter.isPresent() && m != taunter.get()) {
            __instance.cantUseMessage = CardCrawlGame.languagePack.getPowerStrings(TauntPower.POWER_ID).DESCRIPTIONS[1];
            return SpireReturn.Return(false);
        }
        return SpireReturn.Continue();
    }
}