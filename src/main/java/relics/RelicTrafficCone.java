package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.HashSet;
import java.util.Set;

public class RelicTrafficCone extends ReliquaryRelic {
    public static final String ID = "reliquary:TrafficCone";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/trafficCone.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/trafficCone.png");

    static int STRENGTH_LOSS = 2;

    Set<AbstractMonster> triggeredThisCombat;

    public RelicTrafficCone() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
        triggeredThisCombat = new HashSet<>();
    }

    @Override
    public void atBattleStart() {
        triggeredThisCombat.clear();
    }

    // trigger implemented in PatchTrafficCone
    public void triggerIfFirstTime(AbstractMonster m) {
        if (triggeredThisCombat.contains(m) ||
            m.intent == AbstractMonster.Intent.ATTACK ||
            m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
            m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
            m.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
            return;
        }
        addToBot(new RelicAboveCreatureAction(m, this));
        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -STRENGTH_LOSS)));
        triggeredThisCombat.add(m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH_LOSS + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTrafficCone();
    }
}
