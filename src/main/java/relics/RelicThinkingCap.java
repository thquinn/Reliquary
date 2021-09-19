package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelicThinkingCap extends CustomRelic {
    public static final String ID = "reliquary:ThinkingCap";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/thinkingCap.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/thinkingCap.png");

    static int QUEUE_SIZE = 2;
    static List<UUID> UUID_QUEUE = new ArrayList<UUID>();

    public RelicThinkingCap() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public static void onStartBattleStatic() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractRelic thinkingCap = p.getRelic(ID);
        if (thinkingCap != null && UUID_QUEUE.size() == QUEUE_SIZE) {
            UUID uuid = UUID_QUEUE.get(0);
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, thinkingCap));
            AbstractDungeon.actionManager.addToTop(new FetchAction(p.drawPile, c -> c.uuid.equals(uuid), 1));
        }
        UUID_QUEUE.clear();
    }
    public static void onCardUseStatic(AbstractCard card) {
        if (AbstractDungeon.player.masterDeck.group.stream().anyMatch(c -> c.uuid.equals(card.uuid))) {
            UUID_QUEUE.add(card.uuid);
            if (UUID_QUEUE.size() > QUEUE_SIZE) {
                UUID_QUEUE.remove(0);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicThinkingCap();
    }
}
