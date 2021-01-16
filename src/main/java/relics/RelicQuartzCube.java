package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

public class RelicQuartzCube extends CustomRelic {
    public static final String ID = "reliquary:QuartzCube";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/quartzCube.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/outlines/quartzCube.png");

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
            int enemyCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            if (enemyCount <= 1)
                return;
            target = AbstractDungeon.getCurrRoom().monsters.monsters.get(MathUtils.random(enemyCount - 1));
        }
        // This has to be done here: stunning a monster in atBattleStart/atTurnStart causes its intent to never get set.
        if (target.intent != AbstractMonster.Intent.DEBUG) {
            activated = true;
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StunMonsterPower(target, 2)));
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new InvinciblePower(target, 0)));
            flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicQuartzCube();
    }
}
