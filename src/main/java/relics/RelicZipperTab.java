package relics;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import util.TextureLoader;

import java.util.HashSet;

public class RelicZipperTab extends CustomRelic {
    public static final String ID = "reliquary:ZipperTab";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/zipperTab.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/zipperTab.png");

    public RelicZipperTab() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.player == null || AbstractDungeon.player.masterDeck == null) {
            return;
        }
        String id = getDuplicateUnupgradedCardID();
        if (id == null) {
            return;
        }
        AbstractCard first = AbstractDungeon.player.masterDeck.group.stream().filter(c -> !c.upgraded && c.cardID.equals(id)).findFirst().orElse(null);
        if (first != null) {
            first.upgrade();
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(first.makeStatEquivalentCopy()));
        }
        float displayCount = 0;
        for (int i = AbstractDungeon.player.masterDeck.group.size() - 1; i >= 0; i--) {
            AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
            if (!card.upgraded && card.cardID.equals(id)) {
                card.untip();
                card.unhover();
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
                displayCount += Settings.WIDTH / 6.0F;
                AbstractDungeon.player.masterDeck.removeCard(card);
                for (AbstractGameEffect effect : AbstractDungeon.effectsQueue) {
                    if (effect instanceof FastCardObtainEffect) {
                        AbstractCard effectCard = ReflectionHacks.getPrivate(effect, FastCardObtainEffect.class, "card");
                        if (effectCard == card) {
                            effect.isDone = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    public String getDuplicateUnupgradedCardID() {
        HashSet<String> ids = new HashSet<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.upgraded) continue;
            if (card.rarity == AbstractCard.CardRarity.COMMON || card.rarity == AbstractCard.CardRarity.UNCOMMON || card.rarity == AbstractCard.CardRarity.RARE) {
                if (ids.contains(card.cardID)) return card.cardID;
                ids.add(card.cardID);
            }
        }
        return null;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicZipperTab();
    }
}
