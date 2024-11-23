package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.List;

public class RelicCrusadersMap extends ReliquaryRelic {
    public static final String ID = "reliquary:CrusadersMap";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/crusadersMap.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/crusadersMap.png");

    static final float CHANCE = .33f;

    public RelicCrusadersMap() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        return !Settings.isEndless && AbstractDungeon.floorNum > 24 && AbstractDungeon.floorNum <= 48;
    }

    @Override
    public void onChestOpenAfter(boolean bossChest) {
        if (!bossChest) {
            activate();
        }
    }
    public void activate() {
        if (counter == -2 || MathUtils.random() > CHANCE) {
            return;
        }
        List<RewardItem> rewards = AbstractDungeon.getCurrRoom().rewards;
        RewardItem relicReward = rewards.stream().filter(r -> r.type == RewardItem.RewardType.RELIC).findAny().orElse(null);
        if (relicReward == null) {
            return;
        }
        relicReward.relic = new RelicHolyGrail();
        relicReward.text = relicReward.relic.name;
        flash();
        counter = -2;
        setDescription();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (counter == -2) {
            setDescription();
        }
    }

    public void setDescription() {
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        if (counter == -2) {
            grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return counter == -2 ? DESCRIPTIONS[1] : DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicCrusadersMap();
    }
}
