package cards.colorless;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import util.ReliquaryLogger;
import vfx.PearlescenceDisappearEffect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class CardPearlescence extends CustomCard {
    public static final String ID = "reliquary:Pearlescence";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/pearlescence.png";
    private static final ShaderProgram PEARLESCENCE_SHADER = new ShaderProgram(
            SpriteBatch.createDefaultShader().getVertexShaderSource(),
            Gdx.files.internal("reliquaryAssets/shaders/PearlescenceShader.glsl").readString(String.valueOf(StandardCharsets.UTF_8))
    );
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;
    public static final int PATCH_PRICE = -100;

    public AbstractCard lastCard;
    float time;

    public CardPearlescence() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        glowColor = Color.WHITE;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.purgeOnUse) {
            return;
        }
        boolean firstCard = lastCard == null;
        lastCard = c.makeStatEquivalentCopy();
        cardID = c.cardID;
        type = lastCard.type;
        target = c.target;
        cost = lastCard.cost;
        costForTurn = lastCard.costForTurn;
        if (firstCard) {
            lastCard.transparency = 0.01f;
        }
        lastCard.price = PATCH_PRICE; // set for PatchTwinPearls to display red energy correctly
    }
    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.effectsQueue.add(new PearlescenceDisappearEffect(this));
    }
    @Override
    public void triggerOnManualDiscard() {
        AbstractDungeon.effectsQueue.add(new PearlescenceDisappearEffect(this));
    }
    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.effectsQueue.add(new PearlescenceDisappearEffect(this));
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null && !p.hand.contains(this) && lastCard != null) {
            transparency = lastCard.transparency;
        }
        super.update();
        if (lastCard != null) {
            lastCard.update();
        }
        retain = false;
        selfRetain = false;
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        return lastCard != null;
    }

    @Override
    public boolean hasEnoughEnergy() {
        return lastCard == null ? super.hasEnoughEnergy() : lastCard.hasEnoughEnergy();
    }
    @Override
    public boolean canPlay(AbstractCard card) {
        return lastCard == null ? super.canPlay(card) : lastCard.canPlay(card);
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (lastCard == null) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        boolean canUse = lastCard.canUse(p, m);
        cantUseMessage = lastCard.cantUseMessage;
        return canUse;
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (lastCard != null) {
            lastCard.calculateCardDamage(mo);
        }
    }
    @Override
    public void applyPowers() {
        if (lastCard != null) {
            lastCard.applyPowers();
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (lastCard != null) {
            lastCard.use(p, m);
            lastCard.fadingOut = true;
            AbstractDungeon.player.limbo.addToBottom(lastCard); // put it somewhere it will keep updating to finish fading out
            stopGlowing();
        }
    }

    @Override
    public boolean canUpgrade() {
        return lastCard == null ? false : lastCard.canUpgrade();
    }
    @Override
    public void upgrade() {
        if (lastCard != null) {
            lastCard.upgrade();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardPearlescence();
    }
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        CardPearlescence copy = (CardPearlescence) super.makeStatEquivalentCopy();
        copy.cardID = cardID;
        copy.lastCard = lastCard;
        return copy;
    }

    @Override
    public void render(SpriteBatch sb) {
        try {
            Method updateGlow = AbstractCard.class.getDeclaredMethod("updateGlow");
            updateGlow.setAccessible(true);
            updateGlow.invoke(this);
            Method renderGlow = AbstractCard.class.getDeclaredMethod("renderGlow", SpriteBatch.class);
            renderGlow.setAccessible(true);
            renderGlow.invoke(this, sb);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in CardPearlescence glow: " + e);
        }
        renderPearlescentBG(sb);
        if (lastCard == null) {
            return;
        }
        lastCard.current_x = current_x;
        lastCard.current_y = current_y;
        lastCard.drawScale = drawScale;
        lastCard.angle = angle;
        lastCard.render(sb);
    }
    @Override
    public void renderInLibrary(SpriteBatch sb) {
        renderPearlescentBG(sb);
    }
    void renderPearlescentBG(SpriteBatch sb) {
        ShaderProgram oldShader = sb.getShader();
        sb.setShader(PEARLESCENCE_SHADER);
        time += Gdx.graphics.getDeltaTime();
        PEARLESCENCE_SHADER.setUniformf("x_time", time);
        PEARLESCENCE_SHADER.setUniformf("alpha", transparency);
        try {
            Method renderCardBg = AbstractCard.class.getDeclaredMethod("renderCardBg", SpriteBatch.class, float.class, float.class);
            renderCardBg.setAccessible(true);
            renderCardBg.invoke(this, sb, current_x, current_y);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in CardPearlescence bg: " + e);
        }
        sb.setShader(oldShader);
    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        if (lastCard != null) {
            try {
                Field renderTip = AbstractCard.class.getDeclaredField("renderTip");
                renderTip.setAccessible(true);
                renderTip.set(lastCard, renderTip.get(this));
            } catch (Exception e) {
                ReliquaryLogger.error("reflection failed in CardPearlescence tip: " + e);
            }
            lastCard.renderCardTip(sb);
        } else {
            super.renderCardTip(sb);
        }
    }
}