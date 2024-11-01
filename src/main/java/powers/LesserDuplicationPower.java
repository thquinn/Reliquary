package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.StaticHelpers;
import util.TextureLoader;

public class LesserDuplicationPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:LesserDuplication";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/lesserDuplication32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/lesserDuplication84.png");

    public LesserDuplicationPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        this.amount = amount;
        updateDescription();
    }

    public void updateDescription() {
        if (amount == 1) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.costForTurn > 1 || card.cost == -1) {
            return;
        }
        if (!card.purgeOnUse && amount > 0) {
            flash();
            StaticHelpers.duplicatePlayedCard(card, (AbstractMonster) action.target);
            amount--;
            if (amount == 0)
                addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }

    public void atEndOfRound() {
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        } else {
            addToBot(new ReducePowerAction(owner, owner, POWER_ID, 1));
        }
    }
}
