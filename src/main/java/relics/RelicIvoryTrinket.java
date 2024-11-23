package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicIvoryTrinket extends ReliquaryRelic {
    public static final String ID = "reliquary:IvoryTrinket";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ivoryTrinket.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ivoryTrinket.png");

    static int NTH = 5;

    public RelicIvoryTrinket() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID, RelicType.PURPLE);
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
        while (counter >= NTH) {
            addToTop(new MakeTempCardInHandAction(new Miracle(), 1, false));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
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
        return new RelicIvoryTrinket();
    }
}
