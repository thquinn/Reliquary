package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import relics.RelicSideboard;
import util.ReliquaryLogger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class FastCardToSideboardEffect extends AbstractGameEffect {
    private AbstractCard card;

    public FastCardToSideboardEffect(AbstractCard card, float x, float y) {
        CardHelper.obtain(card.cardID, card.rarity, card.color);
        this.card = card;
        duration = 0.01F;
        card.current_x = x;
        card.current_y = y;
        CardCrawlGame.sound.play("CARD_SELECT");
    }

    public void update() {
        if (isDone)
            return;
        duration -= Gdx.graphics.getDeltaTime();
        card.update();
        if (duration < 0.0F) {
            isDone = true;
            card.shrink();
            SoulGroupObtainToSideboard(AbstractDungeon.getCurrRoom().souls, card);
        }
    }

    public void render(SpriteBatch sb) {
        if (!isDone)
            card.render(sb);
    }

    public void dispose() {}

    static void SoulGroupObtainToSideboard(SoulGroup soulGroup, AbstractCard card) {
        CardCrawlGame.sound.play("CARD_OBTAIN");
        try {
            Field soulsField = SoulGroup.class.getDeclaredField("souls");
            soulsField.setAccessible(true);
            List<Soul> souls = (List<Soul>) soulsField.get(soulGroup);
            for (Soul s : souls) {
                if (s.isReadyForReuse) {
                    SoulObtainToSideboard(s, card);
                    return;
                }
            }
            Soul s = new Soul();
            SoulObtainToSideboard(s, card);
            souls.add(s);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in SoulGroupObtainToSideboard: " + e);
        }
    }
    static void SoulObtainToSideboard(Soul soul, AbstractCard card) {
        soul.card = card;
        RelicSideboard sideboard = (RelicSideboard) AbstractDungeon.player.getRelic(RelicSideboard.ID);
        if (sideboard == null) {
            ReliquaryLogger.error("Soul could not find sideboard!");
            return;
        }
        soul.group = sideboard.cards;
        soul.group.addToTop(card);
        try {
            Field posField = Soul.class.getDeclaredField("pos");
            posField.setAccessible(true);
            posField.set(soul, new Vector2(card.current_x, card.current_y));
            Field targetField = Soul.class.getDeclaredField("target");
            targetField.setAccessible(true);
            targetField.set(soul, new Vector2(Settings.WIDTH - 256.0F * Settings.scale, Settings.HEIGHT - 32.0F * Settings.scale));
            Method setSharedVariables = Soul.class.getDeclaredMethod("setSharedVariables");
            setSharedVariables.setAccessible(true);
            setSharedVariables.invoke(soul);
        } catch (Exception e) {
            ReliquaryLogger.error("reflection failed in SoulObtainToSideboard: " + e);
        }
    }
}
