package relics;

import actions.TriggerArtifactAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RelicQuartzCube extends CustomRelic {
    public static final String ID = "reliquary:QuartzCube";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/quartzCube.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/quartzCube.png");

    static int DURATION = 2;

    boolean activated = false, added = false, removed = false;
    AbstractMonster target;

    public RelicQuartzCube() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        activated = false;
        target = null;
        added = false;
        removed = false;
    }

    @Override
    public void update() {
        super.update();

        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return;

        if (target != null && activated) {
            boolean invincible = target.hasPower(StunMonsterPower.POWER_ID);
            if (invincible) {
                added = true;
            } else if (added && !removed) {
                addToBot(new RemoveSpecificPowerAction(target, target, InvinciblePower.POWER_ID));
                removed = true;
            }
        }
        if (activated)
            return;
        if (target == null) {
            if (AbstractDungeon.getCurrRoom().monsters.monsters.size() <= 1) {
                activated = true;
                return;
            }
            chooseTarget();
        }
        // This has to be done here: stunning a monster in atBattleStart/atTurnStart causes its intent to never get set.
        if (target.intent != AbstractMonster.Intent.DEBUG) {
            activated = true;
            addToBot(new RelicAboveCreatureAction(target, this));
            if (target.hasPower("Artifact")) {
                addToBot(new TriggerArtifactAction(target));
                return;
            }
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StunMonsterPower(target, DURATION)));
            IntangiblePower intangiblePower = new IntangiblePower(target, DURATION);
            intangiblePower.atEndOfTurn(false); // trick Intangible into decrementing immediately
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, intangiblePower));
        }
    }

    public void chooseTarget() {
        List<Integer> indices = IntStream.range(0, AbstractDungeon.getCurrRoom().monsters.monsters.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(indices);
        // Pick a random enemy from among the weakest category present (minion, normal, elite, boss).
        boolean found = chooseTargetHelper(indices, i -> AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hasPower(MinionPower.POWER_ID)) ||
                        chooseTargetHelper(indices, i -> AbstractDungeon.getCurrRoom().monsters.monsters.get(i).type == AbstractMonster.EnemyType.NORMAL) ||
                        chooseTargetHelper(indices, i -> AbstractDungeon.getCurrRoom().monsters.monsters.get(i).type == AbstractMonster.EnemyType.ELITE) ||
                        chooseTargetHelper(indices, i -> AbstractDungeon.getCurrRoom().monsters.monsters.get(i).type == AbstractMonster.EnemyType.BOSS);
        assert found;
    }
    public boolean chooseTargetHelper(List<Integer> indices, Predicate<Integer> p) {
        int index = indices.stream().filter(p).findFirst().orElse(-1);
        if (index != -1) {
            target = AbstractDungeon.getCurrRoom().monsters.monsters.get(index);
            return true;
        }
        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DURATION + DESCRIPTIONS[1] + DURATION + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicQuartzCube();
    }
}
