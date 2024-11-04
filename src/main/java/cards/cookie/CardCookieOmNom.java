package cards.cookie;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import util.ReliquaryLogger;

public class CardCookieOmNom extends CardCookie {
    public static final String ID = "reliquary:CookieOmNom";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/omNom.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    boolean inHandLastFrame, drawnThisTurn;

    public CardCookieOmNom() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardTarget.ALL_ENEMY);
        baseDamage = 12;
        isMultiDamage = true;
        selfRetain = true;
    }

    @Override
    public void update() {
        super.update();
        // In-hand check has to include limbo because retained cards briefly travel there at end of turn.
        boolean inHand = AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.limbo.contains(this);
        if (!inHandLastFrame && inHand) {
            ReliquaryLogger.log("Drew Om Nom.");
            drawnThisTurn = true;
        }
        inHandLastFrame = inHand;
    }
    @Override
    public void onRetained() {
        drawnThisTurn = false;
        ReliquaryLogger.log("Om Nom retained.");
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (drawnThisTurn) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}