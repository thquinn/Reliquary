package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class RelicKnoch extends CustomRelic {
    public static final String ID = "reliquary:Knoch";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/knoch.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/outlines/knoch.png");

    Map<AbstractMonster, Integer> waitTicks = new HashMap<>();

    public RelicKnoch() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }

        waitTicks.replaceAll((k, v) -> v - 1);
        waitTicks.values().removeIf(f -> f == 0f);
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster.hasPower(ArtifactPower.POWER_ID)) {
                continue;
            }
            if (waitTicks.containsKey(monster)) {
                continue;
            }
            if (monster.currentHealth <= monster.maxHealth / 4 && !monster.hasPower(WeakPower.POWER_ID)) {
                boolean alreadyApplied = false;
                for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                    if (action instanceof ApplyPowerAction && action.actionType == AbstractGameAction.ActionType.POWER && action.amount == 1 && action.target == monster) {
                        alreadyApplied = true;
                        break;
                    }
                }
                if (alreadyApplied) {
                    continue;
                }
                addToBot(new RelicAboveCreatureAction(monster, this));
                addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, 1, false)));
                waitTicks.put(monster, 30);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicKnoch();
    }
}
