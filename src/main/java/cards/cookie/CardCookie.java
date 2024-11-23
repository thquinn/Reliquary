package cards.cookie;

import actions.CookieCardAnimatePlayAction;
import actions.CookieCardBiteAction;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import cards.ReliquaryCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relics.RelicCookieJar;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public abstract class CardCookie extends ReliquaryCard implements CustomSavable<Integer> {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicCookieJar.ID).DESCRIPTIONS;
    static TextureAtlas.AtlasRegion[] SILHOUETTES_ATTACK, SILHOUETTES_POWER, SILHOUETTES_SKILL;
    int displayBites, displayTimesUpgraded;

    public CardCookie(String id,
                     String name,
                     String img,
                     int cost,
                     String rawDescription,
                     AbstractCard.CardType type,
                     AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, CookieStatics.RELIQUARY_COOKIE, CardRarity.SPECIAL, target);
        // Set frames (from CustomCard.setPortraitTextures).
        bannerSmallRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/banner512.png");
        bannerSmallRegion.offsetX = ImageMaster.CARD_BANNER_RARE.offsetX;
        bannerSmallRegion.offsetY = ImageMaster.CARD_BANNER_RARE.offsetY;
        bannerSmallRegion.originalWidth = bannerSmallRegion.originalHeight = 512;
        bannerLargeRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/banner1024.png");
        bannerLargeRegion.offsetX = ImageMaster.CARD_BANNER_RARE_L.offsetX;
        bannerLargeRegion.offsetY = ImageMaster.CARD_BANNER_RARE_L.offsetY;
        bannerLargeRegion.originalWidth = bannerLargeRegion.originalHeight = 1024;
        switch(type) {
            case ATTACK:
                frameSmallRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/frame_attack512.png");
                frameSmallRegion.offsetX = ImageMaster.CARD_FRAME_ATTACK_RARE.offsetX;
                frameSmallRegion.offsetY = ImageMaster.CARD_FRAME_ATTACK_RARE.offsetY;
                frameLargeRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/frame_attack1024.png");
                frameLargeRegion.offsetX = ImageMaster.CARD_FRAME_ATTACK_RARE_L.offsetX;
                frameLargeRegion.offsetY = ImageMaster.CARD_FRAME_ATTACK_RARE_L.offsetY;
                break;
            case POWER:
                frameSmallRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/frame_power512.png");
                frameSmallRegion.offsetX = ImageMaster.CARD_FRAME_POWER_RARE.offsetX;
                frameSmallRegion.offsetY = ImageMaster.CARD_FRAME_POWER_RARE.offsetY;
                frameLargeRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/frame_power1024.png");
                frameLargeRegion.offsetX = ImageMaster.CARD_FRAME_POWER_RARE_L.offsetX;
                frameLargeRegion.offsetY = ImageMaster.CARD_FRAME_POWER_RARE_L.offsetY;
                break;
            default:
                frameSmallRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/frame_skill512.png");
                frameSmallRegion.offsetX = ImageMaster.CARD_FRAME_SKILL_RARE.offsetX;
                frameSmallRegion.offsetY = ImageMaster.CARD_FRAME_SKILL_RARE.offsetY;
                frameLargeRegion = TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/frame_skill1024.png");
                frameLargeRegion.offsetX = ImageMaster.CARD_FRAME_SKILL_RARE_L.offsetX;
                frameLargeRegion.offsetY = ImageMaster.CARD_FRAME_SKILL_RARE_L.offsetY;
        }
        frameSmallRegion.originalWidth = frameSmallRegion.originalHeight = 512;
        frameLargeRegion.originalWidth = frameLargeRegion.originalHeight = 1024;
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "typeColor", new Color(1, 1, 1, 1));
        // Set glow silhouettes.
        if (SILHOUETTES_ATTACK == null) {
            SILHOUETTES_ATTACK = new TextureAtlas.AtlasRegion[] {
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack_bite.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack_bite2.png"),
            };
            SILHOUETTES_POWER = new TextureAtlas.AtlasRegion[] {
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_power.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_power_bite.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_power_bite2.png"),
            };
            SILHOUETTES_SKILL = new TextureAtlas.AtlasRegion[] {
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_skill.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_skill_bite.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_skill_bite2.png"),
            };
        }
    }

    public boolean canSpawn() {
        return true;
    }
    public boolean canSpawnAsFirst() {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CookieCardBiteAction(this));
    }
    public void setBites(int bites) {
        bites = Math.min(bites, 2);
        CardCookie.CookieBiteField.bites.set(this, bites);
        FleetingField.fleeting.set(this, !upgraded && bites >= 2);
    }

    public void onEaten() { }

    @Override
    public void update() {
        int bites = CookieBiteField.bites.get(this);
        if (shouldUpdateDisplay()) {
            displayBites = bites;
            displayTimesUpgraded = timesUpgraded;
            setBackgroundTexture(
                    getBackgroundSmallTexturePath(type, displayBites, displayTimesUpgraded),
                    getBackgroundLargeTexturePath(type, displayBites, displayTimesUpgraded)
            );
        }
        super.update();
    }
    boolean shouldUpdateDisplay() {
        int bites = CookieBiteField.bites.get(this);
        if (bites == displayBites && timesUpgraded == displayTimesUpgraded) {
            return false;
        }
        if (AbstractDungeon.actionManager == null) {
            return true;
        }
        if (AbstractDungeon.actionManager.actions.stream().anyMatch(a -> a instanceof CookieCardAnimatePlayAction)) {
            return false;
        }
        if (!(AbstractDungeon.actionManager.currentAction instanceof CookieCardAnimatePlayAction)) {
            return true;
        }
        return ((CookieCardAnimatePlayAction)AbstractDungeon.actionManager.currentAction).didGetBit();
    }
    public static String getBackgroundSmallTexturePath(CardType type, int bites, int timesUpgraded) {
        int upgradeIndex = Math.max(0, Math.min(timesUpgraded, 1));
        if (type == CardType.ATTACK) {
            return CookieStatics.CARD_BACKS_ATTACK_SMALL[bites][upgradeIndex];
        } else if (type == CardType.POWER) {
            return CookieStatics.CARD_BACKS_POWER_SMALL[bites][upgradeIndex];
        }
        return CookieStatics.CARD_BACKS_SKILL_SMALL[bites][upgradeIndex];
    }
    public static String getBackgroundLargeTexturePath(CardType type, int bites, int timesUpgraded) {
        int upgradeIndex = Math.max(0, Math.min(timesUpgraded, 1));
        if (type == CardType.ATTACK) {
            return CookieStatics.CARD_BACKS_ATTACK_LARGE[bites][upgradeIndex];
        } else if (type == CardType.POWER) {
            return CookieStatics.CARD_BACKS_POWER_LARGE[bites][upgradeIndex];
        }
        return CookieStatics.CARD_BACKS_SKILL_LARGE[bites][upgradeIndex];
    }

    @Override
    public TextureAtlas.AtlasRegion getCardBgAtlas() {
        if (type == CardType.ATTACK) {
            return SILHOUETTES_ATTACK[displayBites];
        } else if (type == CardType.POWER) {
            return SILHOUETTES_POWER[displayBites];
        }
        return SILHOUETTES_SKILL[displayBites];
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return Collections.singletonList(new TooltipInfo(DESCRIPTIONS[1], DESCRIPTIONS[3]));
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        int bites = CookieBiteField.bites.get(this);
        CardCookie copy = (CardCookie) super.makeStatEquivalentCopy();
        copy.setBites(bites);
        return copy;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
        FleetingField.fleeting.set(this, false);
    }

    @Override
    public Integer onSave() {
        return CookieBiteField.bites.get(this);
    }
    @Override
    public void onLoad(Integer bites) {
        setBites(bites);
    }
    @Override
    public Type savedType() {
        // Needed for the CustomSavable implementation to make it to subclasses.
        return Integer.class;
    }

    @SpirePatch(clz = CardCookie.class, method = SpirePatch.CLASS)
    public static class CookieBiteField {
        public static SpireField<Integer> bites = new SpireField<>(() -> 0);
    }
}
