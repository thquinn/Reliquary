package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.*;

public class RelicKnoch extends CustomRelic {
    public static final String ID = "reliquary:Knoch";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/knoch.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/knoch.png");

    Set<AbstractMonster> monsters = new HashSet<>();

    public RelicKnoch() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        monsters.clear();
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }

        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster.hasPower(ArtifactPower.POWER_ID)) {
                continue;
            }
            if (monster.isDeadOrEscaped() || monster.currentHealth > monster.maxHealth / 4) {
                monsters.remove(monster);
                continue;
            }
            if (monsters.contains(monster)) {
                continue;
            }
            if (!monster.hasPower(WeakPower.POWER_ID)) {
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
                monsters.add(monster);
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
