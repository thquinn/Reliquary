package vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import relics.RelicPaperSnowflake;

public class PaperSnowflakeRepickEffect extends AbstractGameEffect {
    public void update() {
        RelicPaperSnowflake paperSnowflake = (RelicPaperSnowflake) AbstractDungeon.player.getRelic(RelicPaperSnowflake.ID);
        if (paperSnowflake != null) {
            paperSnowflake.repick();
        }
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
