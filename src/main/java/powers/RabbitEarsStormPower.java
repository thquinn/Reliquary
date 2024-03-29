package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import util.TextureLoader;

public class RabbitEarsStormPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:StormPlus";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/stormPlus32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/stormPlus84.png");

    public RabbitEarsStormPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && amount > 0) {
            flash();
            int cost = card.costForTurn == -1 ? EnergyPanel.getCurrentEnergy() : card.costForTurn;
            cost = Math.max(1, cost);
            int numLightning = cost * amount;
            for (int i = 0; i < numLightning; i++) {
                addToBot(new ChannelAction(new Lightning()));
            }
        }
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
    }
}
