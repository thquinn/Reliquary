package orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

public class OrbBias extends OrbDataBase {
    public static String ORB_ID = "reliquary:Bias";
    public static String SFX_CHANNEL = "reliquary:SFX_CHANNEL_BIAS";
    public static String SFX_EVOKE = "reliquary:SFX_EVOKE_BIAS";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;
    static final int PASSIVE_AMOUNT = 1;

    public OrbBias() {
        this.ID = ORB_ID;
        this.name = NAME;
        updateDescription();
    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn(Color.SALMON, Color.RED);
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, -PASSIVE_AMOUNT)));
    }

    @Override
    public void onEvoke() { }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play(SFX_CHANNEL, 0.1F);
    }

    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation(Color.SALMON);
        CardCrawlGame.sound.play(SFX_EVOKE, 0.1F);
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTION[0] + passiveAmount + DESCRIPTION[1];
    }

    @Override
    public void applyFocus() {
        passiveAmount = PASSIVE_AMOUNT;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation(new Color(1, .5f, .5f, 1), new Color(.75f, 0, 0, 1), true);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb, new Color(1, .3f, .3f, .75f), new Color(1, .5f, .5f, .33f));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new OrbBias();
    }
}
