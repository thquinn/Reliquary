package cards.colorless.vapor;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardVaporBloodPotion extends CardVapor {
    public static final String ID = "reliquary:VaporBloodPotion";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/bloodPotion.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public CardVaporBloodPotion() {
        super(ID, NAME, IMG_PATH, DESCRIPTION, CardType.SKILL, CardTarget.NONE);
        baseMagicNumber = 10;
        magicNumber = 10;
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, (int)(p.maxHealth * magicNumber / 100f)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(10);
        }
    }

}