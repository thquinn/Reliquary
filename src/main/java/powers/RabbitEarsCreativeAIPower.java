package powers;

import actions.DiscoveryCustomAction;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class RabbitEarsCreativeAIPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:QuantumAI";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/quantumAI32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/quantumAI84.png");

    public RabbitEarsCreativeAIPower(AbstractCreature owner, int amt) {
        name = NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        this.amount = amt;
        updateDescription();
    }

    public void atStartOfTurn() {
        for (int i = 0; i < this.amount; i++) {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
            AbstractCard card2;
            do {
                card2 = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
            } while (card.cardID.equals(card2.cardID));
            CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            cardGroup.addToBottom(card);
            cardGroup.addToBottom(card2);
            addToBot(new DiscoveryCustomAction(cardGroup));
        }
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else if (amount == 2) {
            description = DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
    }
}
