package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.OptionalInt;

public class RelicMedicineBall extends CustomRelic {
    public static final String ID = "reliquary:MedicineBall";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/medicineBall.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/medicineBall.png");

    int lastStrength = 0;
    boolean firstPerBattle = true;

    public RelicMedicineBall() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        lastStrength = 0;
        firstPerBattle = true;
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return;

        OptionalInt optional = AbstractDungeon.getMonsters().monsters.stream().mapToInt(m -> m.hasPower(StrengthPower.POWER_ID) ? m.getPower(StrengthPower.POWER_ID).amount : 0).max();
        int maxStrength = optional.isPresent() ? Math.max(0, optional.getAsInt()) : 0;
        if (maxStrength != lastStrength) {
            if (firstPerBattle) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                firstPerBattle = false;
            } else {
                flash();
            }
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, maxStrength - lastStrength)));
            counter = maxStrength;
            lastStrength = maxStrength;
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
