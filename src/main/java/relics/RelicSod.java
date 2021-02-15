package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicSod extends CustomRelic {
    public static final String ID = "reliquary:Sod";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/sod.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/sod.png");

    static int NTH = 8;

    public RelicSod() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.upgraded) {
            return;
        }
        flash();
        counter++;
        if (counter >= NTH) {
            AbstractCard copy = c.makeStatEquivalentCopy();
            copy.upgrade();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new MakeTempCardInHandAction(copy));
            counter -= NTH;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + NTH + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSod();
    }
}
