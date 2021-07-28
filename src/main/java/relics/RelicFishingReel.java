package relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import powers.HookedPower;
import powers.TriumphPower;
import util.TextureLoader;

public class RelicFishingReel extends CustomRelic implements AlternateCardCostModifier {
    public static final String ID = "reliquary:FishingReel";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/fishingReel.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/fishingReel.png");

    static final int N = 1;

    public RelicFishingReel() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public int getAlternateResource(AbstractCard abstractCard) {
        if (AbstractDungeon.player.hasPower(HookedPower.POWER_ID) || abstractCard.cost == -1 || abstractCard.costForTurn <= EnergyPanel.totalCount) {
            return -1;
        }
        return 999;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int i) {
        if (i > 0) {
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new ApplyPowerAction(p, p, new HookedPower(i)));
            addToTop(new RelicAboveCreatureAction(p, this));
        }
        return 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + N + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicFishingReel();
    }
}
