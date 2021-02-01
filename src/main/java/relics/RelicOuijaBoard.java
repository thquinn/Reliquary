package relics;

import actions.OuijaBoardAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import util.TextureLoader;

public class RelicOuijaBoard extends CustomRelic implements ClickableRelic {
    public static final String ID = "reliquary:OuijaBoard";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ouijaBoard.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ouijaBoard.png");

    private boolean usedThisTurn = false;
    public CardGroup validTargets = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public RelicOuijaBoard() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        usedThisTurn = false;
    }
    public void CanUseThisTurn() {
        usedThisTurn = false;
        beginLongPulse();
    }


    @Override
    public void onRightClick() {
        if (IsInappropriate()) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (usedThisTurn) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[1], true));
            return;
        }
        if (validTargets.size() == 0) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[p.discardPile.isEmpty() ? 2 : 3], true));
            return;
        }
        // this can probably be replaced with a SelectCardsAction
        addToBot(new OuijaBoardAction(1));
        usedThisTurn = true;
        stopPulse();
    }

    @Override
    public void update() {
        super.update();
        if (IsInappropriate() || usedThisTurn) {
            return;
        }
        validTargets.clear();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.costForTurn + 1 <= EnergyPanel.totalCount) {
                validTargets.addToBottom(c);
            }
        }
        if (validTargets.isEmpty() && pulse) {
            stopPulse();
        } else if (validTargets.size() > 0 && !pulse) {
            beginLongPulse();
        }
    }

    private boolean IsInappropriate() {
        return !isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.actionManager.turnHasEnded || !AbstractDungeon.actionManager.actions.isEmpty();
    }

    @Override
    public String getUpdatedDescription() {
        return CLICKABLE_DESCRIPTIONS()[0] + " NL " + DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicOuijaBoard();
    }
}
