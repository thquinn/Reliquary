package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.PhantasmalPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import powers.TriumphPower;
import util.TextureLoader;

public class RelicStimpack extends CustomRelic {
    public static final String ID = "reliquary:Stimpack";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/stimpack.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/stimpack.png");

    boolean triggeredThisCombat;

    public RelicStimpack() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        triggeredThisCombat = false;
        grayscale = false;
    }

    @Override
    public void onBloodied() {
        if (!triggeredThisCombat) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(p, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DoubleDamagePower(p, 1, AbstractDungeon.actionManager.turnHasEnded), 1));
            triggeredThisCombat = true;
            grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicStimpack();
    }
}
