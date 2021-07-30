package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.List;

public class RelicSpinner extends CustomRelic {
    public static final String ID = "reliquary:Spinner";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/spinner.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/spinner.png");

    static int BLOCK_AMOUNT = 1;

    boolean cardPlayedThisTurn;
    AbstractCard.CardType previousType;

    public RelicSpinner() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        cardPlayedThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        AbstractPlayer p = AbstractDungeon.player;
        if (cardPlayedThisTurn && c.type != previousType) {
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new GainBlockAction(p, p, BLOCK_AMOUNT));
        }
        cardPlayedThisTurn = true;
        previousType = c.type;
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTop) {
        super.renderCounter(sb, inTop);
        List<AbstractCard> cptt = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (cptt.size() == 0) {
            return;
        }
        AbstractCard.CardType lastType = cptt.get(cptt.size() - 1).type;
        String s;
        Color color;
        switch (lastType) {
            case ATTACK:
                s = "ATK";
                color = Color.RED;
                break;
            case SKILL:
                s = "SKL";
                color = Color.GREEN;
                break;
            case POWER:
                s = "PWR";
                color = Color.SKY;
                break;
            case STATUS:
                s = "STA";
                color = Color.LIGHT_GRAY;
                break;
            case CURSE:
                s = "CRS";
                color = Color.GRAY;
                break;
            default:
                s = "???";
                color = Color.WHITE;
        }
        FontHelper.renderFontRightTopAligned(sb,
                                             FontHelper.topPanelInfoFont,
                                             s, hb.cX - ((hb.x - hb.cX)/2) + 30f * Settings.scale,
                                             this.currentY - 36.0F * Settings.scale, .66f, color);
        FontHelper.topPanelInfoFont.getData().setScale(1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_AMOUNT + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSpinner();
    }
}
