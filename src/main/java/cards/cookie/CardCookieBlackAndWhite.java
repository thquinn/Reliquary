package cards.cookie;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.BlackAndWhitePower;
import powers.SnackBreakPower;

public class CardCookieBlackAndWhite extends CardCookie {
    public static final String ID = "reliquary:CookieBlackAndWhite";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/blackAndWhite.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public CardCookieBlackAndWhite() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new ApplyPowerAction(p, p, new BlackAndWhitePower(p, 1)));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}