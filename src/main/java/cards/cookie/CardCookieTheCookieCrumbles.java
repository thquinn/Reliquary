package cards.cookie;

import actions.TheCookieCrumblesAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.ExhaustAllNonAttackAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.SweetDreamsPower;

import java.util.function.Function;
import java.util.stream.Stream;

public class CardCookieTheCookieCrumbles extends CardCookie {
    public static final String ID = "reliquary:CookieTheCookieCrumbles";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/theCookieCrumbles.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 3;

    public CardCookieTheCookieCrumbles() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardTarget.ENEMY);
        baseMagicNumber = 2;
        magicNumber = 2;
        exhaust = true;
        isSnack = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new TheCookieCrumblesAction(m, magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}