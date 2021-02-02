package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import relics.RelicSolitaire;

import java.util.ArrayList;

public class SolitaireLessonLearnedAction extends AbstractGameAction {
    private DamageInfo info;

    private AbstractCard theCard = null;

    public SolitaireLessonLearnedAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        actionType = AbstractGameAction.ActionType.DAMAGE;
        duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_MED &&
                target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            target.damage(info);
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                ArrayList<AbstractCard> possibleCards = new ArrayList<>();
                // Add cards that can be double upgraded.
                theCard = getUpgradeableCard(true);
                if (theCard == null) {
                    theCard = getUpgradeableCard(false);
                }
                if (theCard != null) {
                    theCard.upgrade();
                    theCard.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
                }
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
        if (isDone && theCard != null) {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }

    AbstractCard getUpgradeableCard(boolean twice) {
        if (twice && !AbstractDungeon.player.hasRelic(RelicSolitaire.ID)) {
            return null;
        }
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(SearingBlow.ID)) {
                possibleCards.add(c);
            } else if (twice && c.color == AbstractCard.CardColor.PURPLE && c.timesUpgraded == 0)
                possibleCards.add(c);
            else if (!twice && c.canUpgrade()) {
                possibleCards.add(c);
            }
        }
        if (possibleCards.isEmpty()) {
            return null;
        }
        return possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
    }
}