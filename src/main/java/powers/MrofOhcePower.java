package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.util.ArrayList;

public class MrofOhcePower extends AbstractPower {
    public static final String POWER_ID = "reliquary:MrofOhce";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/mrofOhce32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/mrofOhce84.png");

    boolean triggered = false;
    int triggers = 0;
    int index = -1;
    public static ArrayList<AbstractMonster> cardsPlayedThisTurnTargets = new ArrayList<>();

    public MrofOhcePower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        this.amount = amount;
        updateDescription();
    }

    public void updateDescription() {
        if (amount == 1) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        triggered = false;
        index = -1;
    }

    public boolean beforeEndOfTurn() {
        if (!triggered) {
            ReliquaryLogger.log("setting variables. monsters are:");
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                ReliquaryLogger.log(Integer.toString(m.hashCode()));
            }
            triggered = true;
            triggers = amount;
            index = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1;
            flash();
        }
        ReliquaryLogger.log("entered MrofOhce.atEndOfTurn. index is " + index + ", triggers is " + triggers);
        if (triggers <= 0) {
            ReliquaryLogger.log("no more triggers.");
            return false;
        }
        for (; index >= 0; index--) {
            AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(index);
            if (card.purgeOnUse) {
                ReliquaryLogger.log("card was purge: " + card.cardID);
                continue;
            }
            AbstractMonster monster = cardsPlayedThisTurnTargets.get(index);
            if (monster != null && (monster.isDeadOrEscaped() || monster.halfDead)) {
                ReliquaryLogger.log("monster " + monster.hashCode() + " was dead: " + card.cardID);
                continue;
            }
            ReliquaryLogger.log("trying to mrof card: " + card.cardID);
            if (monster != null) {
                ReliquaryLogger.log("against monster: " + monster.hashCode() + " with " + monster.currentHealth + " HP.");
            }
            card = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(card);
            card.calculateCardDamage(monster);
            card.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, monster, card.energyOnUse, true, true), true);
            triggers--;
            if (index-- == 0) {
                triggers = 0;
            }
            return true;
        }
        ReliquaryLogger.log("no more cards.");
        return false;
    }
}
