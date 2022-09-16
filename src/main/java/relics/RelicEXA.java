package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class RelicEXA extends CustomRelic {
    public static final String ID = "reliquary:EXA";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/exa.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/exa.png");

    public RelicEXA() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        List<String> orbTypes = Stream.of(Lightning.ORB_ID, Frost.ORB_ID, Dark.ORB_ID).collect(toCollection(ArrayList::new));
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            orbTypes.remove(orb.ID);
        }
        if (orbTypes.size() == 0) {
            return;
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        String randomType = orbTypes.get(AbstractDungeon.miscRng.random(orbTypes.size() - 1));
        if (randomType.equals(Lightning.ORB_ID)) {
            addToBot(new ChannelAction(new Lightning()));
        } else if (randomType.equals(Frost.ORB_ID)) {
            addToBot(new ChannelAction(new Frost()));
        } else {
            addToBot(new ChannelAction(new Dark()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicEXA();
    }
}
