package cards.colorless;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import util.ReliquaryLogger;
import util.TextureLoader;

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

    AbstractCard lastCard;
    float time;

    public CardPearlescence() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.cardID.equals(ID)) {
            return;
        }
        lastCard = c.makeStatEquivalentCopy();
        type = lastCard.type;
        target = c.target;
        cost = lastCard.cost;
        costForTurn = lastCard.costForTurn;
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
        return lastCard == null ? false : lastCard.canUse(p, m);
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
        }
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
    public void render(SpriteBatch sb) {
        ShaderProgram oldShader = sb.getShader();
        sb.setShader(PEARLESCENCE_SHADER);
        time += Gdx.graphics.getDeltaTime();
        PEARLESCENCE_SHADER.setUniformf("x_time", time);
        try {
            Method renderCardBg = AbstractCard.class.getDeclaredMethod("renderCardBg", SpriteBatch.class, float.class, float.class);
            renderCardBg.setAccessible(true);
            renderCardBg.invoke(this, sb, current_x, current_y);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in CardPearlescence: " + e);
        }
        sb.setShader(oldShader);
        if (lastCard == null) {
            return;
        }
        lastCard.current_x = current_x;
        lastCard.current_y = current_y;
        lastCard.drawScale = drawScale;
        lastCard.angle = angle;
        lastCard.render(sb);
    }
}