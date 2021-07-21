package actions;

import basemod.devcommands.relic.Relic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;
import relics.RelicBoilingFlask;

public class SkeletonKeyObtainPotionAction extends AbstractGameAction {
    AbstractPotion potion;

    public SkeletonKeyObtainPotionAction(AbstractPotion potion) {
        actionType = ActionType.SPECIAL;
        this.potion = potion;
    }

    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        AbstractRelic sozu = p.getRelic(Sozu.ID);
        if (sozu != null) {
            sozu.flash();
            return;
        }
        AbstractDungeon.player.obtainPotion(potion);
        AbstractCard vapor = RelicBoilingFlask.POTION_TO_VAPOR.getOrDefault(potion.ID, RelicBoilingFlask.UNKNOWN_VAPOR).makeCopy();
        addToTop(new MakeTempCardInHandAction(vapor, 1));
    }
}
