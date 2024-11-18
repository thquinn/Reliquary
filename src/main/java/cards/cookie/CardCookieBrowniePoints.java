package cards.cookie;

import actions.BrowniePointsAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardCookieBrowniePoints extends CardCookie {
    public static final String ID = "reliquary:CookieBrowniePoints";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/browniePoints.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardCookieBrowniePoints() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.ENEMY);
        baseMagicNumber = 10;
        magicNumber = 10;
        isSnack = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new BrowniePointsAction(m, magicNumber, 2));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}