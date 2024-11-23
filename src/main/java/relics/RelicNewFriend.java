package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.StaticHelpers;
import util.TextureLoader;

public class RelicNewFriend extends ReliquaryRelic {
    public static final String ID = "reliquary:NewFriend";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/newFriend.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/newFriend.png");

    static int PERCENT_CHANCE = 50;

    public RelicNewFriend() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        StaticHelpers.removeStrikeTips(this);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if ((c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) && MathUtils.random(1, 100) < PERCENT_CHANCE) {
            flash();
            c.exhaustOnUseOnce = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + PERCENT_CHANCE + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicNewFriend();
    }
}
