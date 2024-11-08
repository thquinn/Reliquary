package cards.cookie;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import powers.SweetDreamsPower;

public class CardCookieSweetDreams extends CardCookie {
    public static final String ID = "reliquary:CookieSweetDreams";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/sweetDreams.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardCookieSweetDreams() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = 3;
        magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new ApplyPowerAction(p, p, new SweetDreamsPower(p, magicNumber)));
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}