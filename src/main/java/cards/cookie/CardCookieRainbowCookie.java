package cards.cookie;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.SnackBreakPower;

public class CardCookieRainbowCookie extends CardCookie {
    public static final String ID = "reliquary:CookieRainbowCookie";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/cookies/rainbowCookie.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardCookieRainbowCookie() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new DrawPileToHandAction(1, CardType.ATTACK));
        addToBot(new DrawPileToHandAction(1, CardType.SKILL));
        addToBot(new DrawPileToHandAction(1, CardType.POWER));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}