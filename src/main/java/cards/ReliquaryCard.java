package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class ReliquaryCard extends CustomCard {
    public boolean isSnack;

    public ReliquaryCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if (isSnack) {
            flash();
            addToBot(new DrawCardAction(1));
        }
    }
}
