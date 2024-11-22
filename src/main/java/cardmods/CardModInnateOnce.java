package cardmods;

import actions.RemoveCardModAction;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardModInnateOnce extends AbstractCardModifier {
    public static final String ID = "reliquary:CardModInnateOnce";

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.isInnate;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isInnate = true;
    }

    @Override
    public boolean onBattleStart(AbstractCard card) {
        AbstractCard masterCard = StSLib.getMasterDeckEquivalent(card);
        CardModifierManager.removeModifiersById(masterCard, ID, true);
        return false;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isInnate = false;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CardModInnateOnce();
    }
}
