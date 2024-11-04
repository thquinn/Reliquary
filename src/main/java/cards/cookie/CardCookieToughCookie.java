package cards.cookie;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.SnackBreakPower;

public class CardCookieToughCookie extends CardCookie {
    public static final String ID = "reliquary:CookieToughCookie";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/toughCookie.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 3;

    int maxCostForTurn;

    public CardCookieToughCookie() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        baseBlock = 17;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.costForTurn > maxCostForTurn) {
            int delta = c.costForTurn - maxCostForTurn;
            maxCostForTurn = c.costForTurn;
            setCostForTurn(Math.max(0, costForTurn - delta));
        }
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        maxCostForTurn = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}