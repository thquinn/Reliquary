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
import powers.LoseFocusPower;

public class OrbData extends OrbDataBase {
    public static String ORB_ID = "reliquary:Data";
    public static String SFX_CHANNEL = "reliquary:SFX_CHANNEL_DATA";
    public static String SFX_EVOKE = "reliquary:SFX_EVOKE_DATA";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;
    static final int PASSIVE_AMOUNT = 1;
    static final int EVOKE_AMOUNT = 3;
    private boolean channeled;

    public OrbData(boolean channeled) {
        this.ID = ORB_ID;
        this.name = NAME;
        this.channeled = channeled;
        updateDescription();
    }
    public OrbData() {
        this(false);
    }

    @Override
    public void update() {
        super.update();
        if (!channeled) {
            onStartOfTurn();
            channeled = true;
        }
    }

    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn(Color.LIME, Color.GREEN);
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new LoseFocusPower(p, PASSIVE_AMOUNT)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, PASSIVE_AMOUNT)));
    }

    @Override
    public void onEvoke() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new LoseFocusPower(p, EVOKE_AMOUNT)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, EVOKE_AMOUNT)));
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play(SFX_CHANNEL, 0.1F);
    }

    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation(Color.LIME);
        CardCrawlGame.sound.play(SFX_EVOKE, 0.1F);
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTION[0] + passiveAmount + DESCRIPTION[1] + evokeAmount + DESCRIPTION[2];
    }

    @Override
    public void applyFocus() {
        passiveAmount = PASSIVE_AMOUNT;
        evokeAmount = EVOKE_AMOUNT;
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation(new Color(.5f, 1, 0.5f, 1), new Color(0, .75f, 0, 1), false);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb, new Color(.3f, 1, .3f, .75f), new Color(.5f, 1, .5f, .33f));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new OrbData();
    }
}
