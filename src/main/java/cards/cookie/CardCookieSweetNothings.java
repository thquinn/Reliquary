package cards.cookie;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardCookieSweetNothings extends CardCookie {
    public static final String ID = "reliquary:CookieSweetNothings";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/sweetNothings.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardCookieSweetNothings() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        exhaust = true;
        isSnack = true;
    }
    @Override
    public boolean canSpawn() {
        return CookieStatics.allCardsRemovedFromMasterDeck.size() >= 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        if (CookieStatics.allCardsRemovedFromMasterDeck.isEmpty()) return;
        addToBot(new SelectCardsAction(CookieStatics.allCardsRemovedFromMasterDeck, cardStrings.EXTENDED_DESCRIPTION[0], c -> addToBot(new MakeTempCardInHandAction(c.get(0)))));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}