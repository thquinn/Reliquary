package relics;

import actions.AddCardModToHandAction;
import basemod.abstracts.CustomRelic;
import cardmods.CardModRetain;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicNewFriend extends CustomRelic {
    public static final String ID = "reliquary:NewFriend";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/newFriend.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/newFriend.png");

    static int PERCENT_CHANCE = 50;

    public RelicNewFriend() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
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
