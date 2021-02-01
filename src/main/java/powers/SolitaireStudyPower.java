package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class SolitaireStudyPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:StudyPlus";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/studyPlus32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/studyPlus84.png");

    public SolitaireStudyPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;
    }

    public void atEndOfTurn(boolean playerTurn) {
        AbstractCard card = new Insight();
        card.upgrade();
        addToBot(new MakeTempCardInDrawPileAction(card, amount, true, true));
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
}
