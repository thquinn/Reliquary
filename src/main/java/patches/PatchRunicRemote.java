package patches;

import basemod.helpers.CardModifierManager;
import cardmods.CardModIridiumCopy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import com.megacrit.cardcrawl.rewards.RewardItem;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import relics.RelicRunicRemote;
import util.TextureLoader;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;

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