package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicJackalopeAntler extends CustomRelic {
    public static final String ID = "reliquary:JackalopeAntler";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/jackalopeAntler.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/jackalopeAntler.png");

    int lastCost;

    public RelicJackalopeAntler() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        lastCost = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        lastCost = c.cost == -1 ? c.energyOnUse : c.costForTurn;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (lastCost > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.gainEnergy(lastCost);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicJackalopeAntler();
    }
}
