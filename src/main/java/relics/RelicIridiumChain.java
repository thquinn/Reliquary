package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import cardmods.CardModEthereal;
import cardmods.CardModIncreaseCost;
import cardmods.CardModIridiumCopy;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicIridiumChain extends ReliquaryRelic {
    public static final String ID = "reliquary:IridiumChain";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/iridiumChain.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/iridiumChain.png");

    public RelicIridiumChain() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type != AbstractCard.CardType.POWER || c.cost < 0)
            return;
        AbstractCard copy = c.makeStatEquivalentCopy();
        CardModifierManager.addModifier(copy, new CardModIncreaseCost(1));
        CardModifierManager.addModifier(copy, new CardModEthereal());
        CardModifierManager.addModifier(copy, new CardModIridiumCopy());
        copy.initializeDescription();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInHandAction(copy));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicIridiumChain();
    }
}
