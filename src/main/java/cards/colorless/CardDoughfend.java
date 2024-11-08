package cards.colorless;

import cards.ReliquaryCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CardDoughfend extends ReliquaryCard {
    public static final String ID = "reliquary:CookieSugarcoatDoughfend";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/doughfend.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardDoughfend() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        baseBlock = 0;
        exhaust = true;
        isEthereal = true;
    }

    public void setBlock(int b) {
        baseBlock = b;
        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(10);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardDoughfend();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        copy.description = (ArrayList) description.clone();
        return copy;
    }
}