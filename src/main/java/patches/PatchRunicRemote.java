package patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import relics.RelicRunicRemote;
import util.TextureLoader;

@SpirePatch(
        clz= RewardItem.class,
        method="render"
)
public class PatchRunicRemote {
    static Texture REWARD_CARD_REMOTE = TextureLoader.getTexture("reliquaryAssets/images/ui/remoteCardReward.png");
    static Texture REWARD_CARD_NORMAL = ImageMaster.REWARD_CARD_NORMAL;

    public static void Prefix(RewardItem __instance, SpriteBatch sb) {
        if (__instance.relic != null && __instance.relic.relicId.equals(RelicRunicRemote.ID)) {
            ImageMaster.REWARD_CARD_NORMAL = REWARD_CARD_REMOTE;
        }
    }
    public static void Postfix(RewardItem __instance, SpriteBatch sb) {
        ImageMaster.REWARD_CARD_NORMAL = REWARD_CARD_NORMAL;
    }
}