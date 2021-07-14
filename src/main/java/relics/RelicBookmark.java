package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicBookmark extends CustomRelic {
    public static final String ID = "reliquary:Bookmark";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bookmark.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bookmark.png");

    public RelicBookmark() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    public void atBattleStart() {
        updateCounter();
        if (counter > 0) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(AbstractDungeon.player, counter));
        }
    }

    @Override
    public void onEquip() {
        updateCounter();
    }
    @Override
    public void onMasterDeckChange() {
        updateCounter();
    }
    void updateCounter() {
        counter = (int)AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.isInnate).count();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBookmark();
    }
}
