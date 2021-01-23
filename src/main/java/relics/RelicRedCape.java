package relics;

import actions.ApplyTauntAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicRedCape extends CustomRelic {
    public static final String ID = "reliquary:RedCape";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/redCape.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/redCape.png");

    public RelicRedCape() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type != AbstractCard.CardType.ATTACK || c.target != AbstractCard.CardTarget.ENEMY) {
            return;
        }
        addToBot(new ApplyTauntAction(m, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRedCape();
    }
}
