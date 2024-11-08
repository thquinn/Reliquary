package cards.cookie;

import cards.colorless.CardDoughfend;
import cards.colorless.CardJustDesserts;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.SweetRevengePower;

public class CardCookieSugarcoat extends CardCookie {
    public static final String ID = "reliquary:CookieSugarcoat";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/sugarcoat.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public CardCookieSugarcoat() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        isSnack = true;
        cardsToPreview = new CardDoughfend();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        CardDoughfend doughfend = new CardDoughfend();
        doughfend.setBlock(p.currentBlock);
        addToBot(new MakeTempCardInDrawPileAction(doughfend, 1, true, true));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}