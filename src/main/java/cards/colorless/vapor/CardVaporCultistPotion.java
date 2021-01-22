package cards.colorless.vapor;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CardVaporCultistPotion extends CustomCard {
    public static final String ID = "reliquary:VaporCultistPotion";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/cultistPotion.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final String[] SFXS = new String[] { "VO_CULTIST_1A", "VO_CULTIST_1B", "VO_CULTIST_1C" };

    public CardVaporCultistPotion() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        baseMagicNumber = 3;
        magicNumber = 3;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RitualPower(p, 1, true)));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -magicNumber)));
        addToBot(new SFXAction(SFXS[MathUtils.random(SFXS.length - 1)]));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardVaporCultistPotion();
    }
}