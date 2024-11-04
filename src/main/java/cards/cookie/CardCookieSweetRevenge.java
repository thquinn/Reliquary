package cards.cookie;

import cards.colorless.CardJustDesserts;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.SweetRevengePower;

public class CardCookieSweetRevenge extends CardCookie {
    public static final String ID = "reliquary:CookieSweetRevenge";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/sweetRevenge.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public CardCookieSweetRevenge() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardTarget.SELF);
        cardsToPreview = new CardJustDesserts();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new ApplyPowerAction(p, p, new SweetRevengePower(p)));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}