package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.HashSet;
import java.util.Set;

public class RelicKnoch extends ReliquaryRelic {
    public static final String ID = "reliquary:Knoch";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/knoch.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/knoch.png");

    static int PERCENT = 25;

    Set<AbstractCreature> monstersCheckedForIneligibility, ineligibleMonsters;

    public RelicKnoch() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
        monstersCheckedForIneligibility = new HashSet<>();
        ineligibleMonsters = new HashSet<>();
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        if (AbstractDungeon.actionManager.currentAction instanceof ApplyPowerAction || AbstractDungeon.actionManager.actions.stream().anyMatch(a -> a instanceof ApplyPowerAction)) {
            // Wait for queued ApplyPowerActions to finish before attempting to apply Weak.
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.monsters == null) {
            // We have no monsters.
            return;
        }

        for (AbstractMonster monster : room.monsters.monsters) {
            if (!monstersCheckedForIneligibility.contains(monster)) {
                // Ruina "ally" monsters are in the room's monster list and are immune to debuffs.
                // What are they doing in there? I am not to say.
                Class clazz = monster.getClass();
                while (clazz != null) {
                    if (clazz.getSimpleName().equals("AbstractAllyMonster")) {
                        ineligibleMonsters.add(monster);
                        break;
                    }
                    clazz = clazz.getSuperclass();
                }
                monstersCheckedForIneligibility.add(monster);
            }
            if (ineligibleMonsters.contains(monster)) {
                continue;
            }
            if (monster.hasPower(ArtifactPower.POWER_ID) || monster.hasPower(WeakPower.POWER_ID)) {
                continue;
            }
            if (monster.isDeadOrEscaped() || monster.currentHealth > monster.maxHealth / 100f * PERCENT) {
                continue;
            }
            addToBot(new RelicAboveCreatureAction(monster, this));
            addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, 1, false)));
        }
    }

    @Override
    public void onVictory() {
        monstersCheckedForIneligibility.clear();
        ineligibleMonsters.clear();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + PERCENT + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicKnoch();
    }
}
