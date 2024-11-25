package relics;

import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.CalmStance;
import powers.ReduceColorlessCostPower;
import util.ReliquaryLogger;
import util.TextureLoader;

public class RelicBubbleWand extends ReliquaryRelic {
    public static final String ID = "reliquary:BubbleWand";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bubbleWand.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bubbleWand.png");

    public RelicBubbleWand() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL, RelicType.PURPLE);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.size() == 1) {
            addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBubbleWand();
    }
}
