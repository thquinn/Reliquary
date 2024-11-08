package cards.cookie;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardCookieToutSweet extends CardCookie {
    public static final String ID = "reliquary:CookieToutSweet";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/toutSweet.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    static List<AbstractCard> inDiscardPileAtTurnStart;

    public CardCookieToutSweet() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        exhaust = true;
    }

    public static void onPlayerTurnStartPostDrawStatic() {
        if (inDiscardPileAtTurnStart == null) {
            inDiscardPileAtTurnStart = new ArrayList<>();
        } else {
            inDiscardPileAtTurnStart.clear();
        }
        inDiscardPileAtTurnStart.addAll(AbstractDungeon.player.discardPile.group);
    }
    public static void postUpdateStatic() {
        if (inDiscardPileAtTurnStart == null || AbstractDungeon.player == null || AbstractDungeon.player.discardPile == null) {
            return;
        }
        if (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        for (int i = inDiscardPileAtTurnStart.size() - 1; i >= 0; i--) {
            if (!AbstractDungeon.player.discardPile.contains(inDiscardPileAtTurnStart.get(i))) {
                inDiscardPileAtTurnStart.remove(i);
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new FetchAction(p.discardPile, c -> c.type == CardType.SKILL && !inDiscardPileAtTurnStart.contains(c), 999));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}