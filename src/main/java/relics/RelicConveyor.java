package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.PatchSkipPlayerTurn;
import util.TextureLoader;

import java.util.*;

public class RelicConveyor extends CustomRelic {
    public static final String ID = "reliquary:Conveyor";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/conveyor.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/conveyor.png");

    static int MIN_COUNT = 3;

    public RelicConveyor() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart() {
        Map<AbstractOrb, Integer> counts = new HashMap<>();
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof EmptyOrbSlot) {
                continue;
            }
            Optional<AbstractOrb> match = counts.keySet().stream().filter(o -> o.ID.equals(orb.ID)).findAny();
            if (match.isPresent()) {
               AbstractOrb matchingOrb = match.get();
               counts.put(matchingOrb, counts.get(matchingOrb) + 1);
            } else {
                counts.put(orb, 1);
            }
        }
        AbstractOrb[] doubles = counts.entrySet().stream().filter(e -> e.getValue() >= MIN_COUNT).map(e -> e.getKey()).toArray(AbstractOrb[]::new);
        if (doubles.length == 0) {
            return;
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractOrb copy = doubles[MathUtils.random(0, doubles.length - 1)].makeCopy();
        copy.onStartOfTurn(); // by the time the copy is actually channeled, onStartOfTurn will have missed it, so do it manually
        addToBot(new ChannelAction(copy));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MIN_COUNT + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicConveyor();
    }
}
