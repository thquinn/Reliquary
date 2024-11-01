package cards.colorless.vapor;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class CardVaporHeartOfIron extends CardVapor {
    public static final String ID = "reliquary:VaporHeartOfIron";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/heartOfIron.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public CardVaporHeartOfIron() {
        super(ID, NAME, IMG_PATH, DESCRIPTION, CardType.POWER, CardTarget.NONE);
        baseMagicNumber = 3;
        magicNumber = 3;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}