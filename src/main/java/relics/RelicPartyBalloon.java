package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPartyBalloon extends ReliquaryRelic {
    public static final String ID = "reliquary:PartyBalloon";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/partyBalloon.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/partyBalloon.png");

    public RelicPartyBalloon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.player.drawPile.group.stream().anyMatch(c -> c.type == AbstractCard.CardType.POWER)) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    // implemented in PatchPartyBalloon

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPartyBalloon();
    }
}
