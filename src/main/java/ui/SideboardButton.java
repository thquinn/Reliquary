package ui;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import relics.RelicSideboard;
import util.TextureLoader;

public class SideboardButton extends TopPanelItem {
    private final static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicSideboard.ID).DESCRIPTIONS;
    boolean hovered = false;

    public SideboardButton() {
        super(TextureLoader.getTexture("reliquaryAssets/images/ui/sideboard.png"), "reliquary:SideboardButton");
    }

    @Override
    protected void onClick() {
        RelicSideboard sideboard = (RelicSideboard) AbstractDungeon.player.getRelic(RelicSideboard.ID);
        if (sideboard != null) {
            sideboard.onClickTopPanelItem();
        }
    }

    @Override
    protected void onHover() {
        super.onHover();
        hovered = true;
    }

    @Override
    protected void onUnhover() {
        super.onUnhover();
        hovered = false;
    }

    @Override
    public void render(SpriteBatch sb) {
        RelicSideboard sideboard = (RelicSideboard) AbstractDungeon.player.getRelic(RelicSideboard.ID);
        if (sideboard == null) {
            return;
        }
        super.render(sb);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelAmountFont, Integer.toString(sideboard.cards.size()), x + 58.0f * Settings.scale, y + 25.0f * Settings.scale, Color.WHITE.cpy());
        if (hovered && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {
            sb.setColor(Color.CYAN);
            float topRightTipX = 1550.0F * Settings.scale;
            float tipY = Settings.HEIGHT - 120.0F * Settings.scale;
            TipHelper.renderGenericTip(topRightTipX, tipY, DESCRIPTIONS[1], DESCRIPTIONS[2]);
        }
    }
}