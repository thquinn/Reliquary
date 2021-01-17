package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

public class RelicMedicineBall extends CustomRelic {
    public static final String ID = "reliquary:MedicineBall";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/medicineBall.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/outlines/medicineBall.png");

    int lastStrength = 0;
    boolean firstPerBattle = true;

    public RelicMedicineBall() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        lastStrength = 0;
        firstPerBattle = true;
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return;

        int totalStrength = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.isDeadOrEscaped())
                continue;
            AbstractPower p = m.getPower(StrengthPower.POWER_ID);
            if (p != null)
                totalStrength += p.amount;
        }
        if (totalStrength != lastStrength) {
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, totalStrength - lastStrength)));
            if (firstPerBattle) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                firstPerBattle = false;
            } else {
                flash();
            }
            counter = totalStrength;
            lastStrength = totalStrength;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicMedicineBall();
    }
}
