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

public class RelicJackalopeHorn extends CustomRelic {
    public static final String ID = "reliquary:JackalopeHorn";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/jackalopeHorn.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/jackalopeHorn.png");

    int lastCost;

    public RelicJackalopeHorn() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        lastCost = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        lastCost = c.energyOnUse;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount >= target.currentHealth && lastCost > 0) {
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
        return new RelicJackalopeHorn();
    }
}
