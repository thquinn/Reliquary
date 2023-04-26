package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import powers.DeathWarrantPower;
import util.TextureLoader;

public class RelicPromissoryNotes extends CustomRelic implements OnReceivePowerRelic {
    public static final String ID = "reliquary:PromissoryNotes";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/promissoryNotes.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/promissoryNotes.png");

    static int DAMAGE = 5;

    public RelicPromissoryNotes() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature source) {
        if (source.hasPower(DeathWarrantPower.POWER_ID) && source.getPower(DeathWarrantPower.POWER_ID).amount >= 100) {
            return true;
        }
        if (abstractPower.type == AbstractPower.PowerType.DEBUFF && source != null && !source.isPlayer) {
            addToBot(new RelicAboveCreatureAction(source, this));
            addToBot(new ApplyPowerAction(source, AbstractDungeon.player, new DeathWarrantPower(source, DAMAGE)));
        }
        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPromissoryNotes();
    }
}
