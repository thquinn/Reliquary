package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

public class RelicLeadDart extends CustomRelic {
    public static final String ID = "reliquary:LeadDart";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/leadDart.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/leadDart.png");

    static final int N = 3;
    boolean triggeredThisCombat;

    public RelicLeadDart() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        triggeredThisCombat = false;
        grayscale = false;
    }

    @Override
    public void update() {
        super.update();
        if (triggeredThisCombat || !isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.size() >= 10) {
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, N)));
            triggeredThisCombat = true;
            grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + N + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicLeadDart();
    }
}
