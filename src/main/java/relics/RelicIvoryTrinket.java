package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicIvoryTrinket extends CustomRelic {
    public static final String ID = "reliquary:IvoryTrinket";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ivoryTrinket.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ivoryTrinket.png");

    public RelicIvoryTrinket() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    // event implemented in PatchIvoryTrinket

    public void onRetain() {
        int miracles = (int)AbstractDungeon.player.hand.group.stream().filter(c -> c.cardID.equals(Miracle.ID)).count();
        if (miracles > 0) {
            counter += miracles;
            flash();
        }
        while (counter >= 10) {
            addToTop(new MakeTempCardInHandAction(new Miracle(), 1, false));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            counter -= 10;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicIvoryTrinket();
    }
}
