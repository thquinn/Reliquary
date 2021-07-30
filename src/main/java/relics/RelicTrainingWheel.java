package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicTrainingWheel extends CustomRelic {
    public static final String ID = "reliquary:TrainingWheel";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/trainingWheel.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/trainingWheel.png");

    public RelicTrainingWheel() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    public void atOpeningHand() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean triggered = false;
        if (canAndShouldFetch(AbstractCard.CardType.SKILL)) {
            addToTop(new DrawPileToHandAction(1, AbstractCard.CardType.SKILL));
            triggered = true;
        }
        if (canAndShouldFetch(AbstractCard.CardType.ATTACK)) {
            addToTop(new DrawPileToHandAction(1, AbstractCard.CardType.ATTACK));
            triggered = true;
        }
        if (triggered) {
            addToTop(new RelicAboveCreatureAction(p, this));
        }
    }
    boolean canAndShouldFetch(AbstractCard.CardType type) {
        AbstractPlayer p = AbstractDungeon.player;
        return p.hand.group.stream().noneMatch(c -> c.type == type) && p.drawPile.group.stream().anyMatch(c -> c.type == type);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTrainingWheel();
    }
}
