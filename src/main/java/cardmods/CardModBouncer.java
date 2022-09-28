package cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relics.RelicBouncer;

public class CardModBouncer extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModBouncer";
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicBouncer.ID).DESCRIPTIONS;

    public CardModBouncer() {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return DESCRIPTIONS[4] + rawDescription + DESCRIPTIONS[5];
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if (AbstractDungeon.player.hand.contains(card)) {
            card.cantUseMessage = DESCRIPTIONS[6];
            return false;
        }
        return true;
    }

    public void onDiscard(AbstractCard card) {
        AbstractDungeon.player.discardPile.group.remove(card);
        (AbstractDungeon.getCurrRoom()).souls.remove(card);
        AbstractDungeon.player.limbo.group.add(card);
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(card, target, false, true));
        AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModBouncer();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
