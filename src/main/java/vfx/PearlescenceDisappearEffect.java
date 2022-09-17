package vfx;

import cards.colorless.CardPearlescence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PearlescenceDisappearEffect extends AbstractGameEffect {
    AbstractCard c;

    public PearlescenceDisappearEffect(CardPearlescence c) {
        duration = Settings.ACTION_DUR_LONG;
        this.c = c;
        c.stopGlowing();
        AbstractDungeon.player.limbo.addToTop(c);
        c.fadingOut = true;
        if (c.lastCard != null) {
            c.lastCard.fadingOut = true;
        }
    }

    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        // just get rid of the damn thing
        AbstractDungeon.player.hand.removeCard(c);
        AbstractDungeon.player.discardPile.removeCard(c);
        AbstractDungeon.player.exhaustPile.removeCard(c);
        if (duration < 0.0F) {
            AbstractDungeon.player.limbo.removeCard(c);
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
