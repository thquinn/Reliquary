package cards.colorless.vapor;

import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardVaporEssenceOfDarkness extends CardVapor {
    public static final String ID = "reliquary:VaporEssenceOfDarkness";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/essenceOfDarkness.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public CardVaporEssenceOfDarkness() {
        super(ID, NAME, IMG_PATH, DESCRIPTION, CardType.SKILL, CardTarget.NONE);
        baseMagicNumber = 1;
        magicNumber = 1;
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DarkImpulseAction());
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}