package cards.colorless.vapor;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;

public class CardVaporGhostInAJar extends CardVapor {
    public static final String ID = "reliquary:VaporGhostInAJar";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/ghostInAJar.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public CardVaporGhostInAJar() {
        super(ID, NAME, IMG_PATH, DESCRIPTION, CardType.SKILL, CardTarget.NONE);
        baseMagicNumber = 50;
        magicNumber = 50;
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (MathUtils.random(1, 100) < magicNumber) {
            addToBot(new ApplyPowerAction(p, p, new IntangiblePower(p, 1)));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(40);
        }
    }
}