package cards.cookie;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.SmartCookiePower;
import util.ReliquaryLogger;

public class CardCookieSmartCookie extends CardCookie {
    public static final String ID = "reliquary:CookieSmartCookie";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/smartCookie.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;

    public CardCookieSmartCookie() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardTarget.SELF);
        isSnack = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], cards -> {
            if (cards.size() > 0) {
                addToBot(new ApplyPowerAction(p, p, new SmartCookiePower(p, cards.get(0))));
            }
        }));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}