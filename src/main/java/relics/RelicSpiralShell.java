package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.PatchAfterUseCard;
import powers.ReduceColorlessCostPower;
import util.TextureLoader;

public class RelicSpiralShell extends CustomRelic implements PatchAfterUseCard.AfterUseCardRelic {
    public static final String ID = "reliquary:SpiralShell";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/spiralShell.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/spiralShell.png");

    public RelicSpiralShell() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.masterDeck.group.stream().anyMatch(c -> c.cost == -1);
    }

    @Override
    public void afterUseCard(AbstractCard c, AbstractMonster m) {
        if (c.cost == -1 && c.energyOnUse > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSpiralShell();
    }
}
