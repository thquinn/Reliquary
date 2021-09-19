package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RelicFerryPass extends CustomRelic implements ClickableRelic {
    public static final String ID = "reliquary:FerryPass";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ferryPass.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ferryPass.png");

    static int SPAWN_MIN_UNIQUES = 12;

    boolean allUnplayed;
    Set<String> unplayed;

    public RelicFerryPass() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    String[] getCardIDs() {
        return AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.cost != -2).map(c -> c.cardID).distinct().toArray(String[]::new);
    }

    @Override
    public boolean canSpawn() {
        if (AbstractDungeon.floorNum > 48) {
            return false;
        }
        int numNamesInDeck = getCardIDs().length;
        return numNamesInDeck >= SPAWN_MIN_UNIQUES;
    }

    @Override
    public void onEquip() {
        atBattleStart();
    }

    @Override
    public void onMasterDeckChange() {
        atBattleStart();
    }

    @Override
    public void atBattleStart() {
        if (counter == -2) {
            setDescription();
            return;
        }
        allUnplayed = true;
        unplayed = new HashSet<>(Arrays.asList(getCardIDs()));
        counter = unplayed.size();
        setDescription();
        flash();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (counter == -2) {
            return;
        }
        if (unplayed.contains(c.cardID)) {
            allUnplayed = false;
            unplayed.remove(c.cardID);
            counter--;
            if (counter == 0) {
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
                counter = -2;
                setDescription();
            }
            setDescription();
            flash();
        }
    }

    @Override
    public void onRightClick() {
        if (counter == -2) {
            return;
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (unplayed.contains(c.cardID)) {
                c.flash();
            }
        }
    }

    public void setDescription() {
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if (counter == -2) {
            grayscale = true;
            return DESCRIPTIONS[10];
        }
        if (!isObtained || AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return DESCRIPTIONS[0];
        }
        if (allUnplayed) {
            return DESCRIPTIONS[1] + counter + DESCRIPTIONS[2] + DESCRIPTIONS[9];
        }
        if (counter > 3) {
            return DESCRIPTIONS[3] + counter + DESCRIPTIONS[4] + DESCRIPTIONS[9];
        }
        List<String> names = unplayed.stream().map(id -> FontHelper.colorString(CardCrawlGame.languagePack.getCardStrings(id).NAME, "y")).collect(Collectors.toList());
        if (counter >= 3) {
            return DESCRIPTIONS[5] + String.join(", ", names.subList(0, names.size() - 1)) + DESCRIPTIONS[6] + names.get(names.size() - 1) + DESCRIPTIONS[8] + DESCRIPTIONS[9];
        }
        if (counter == 2) {
            return DESCRIPTIONS[5] + String.join(", ", names.subList(0, names.size() - 1)) + DESCRIPTIONS[7] + names.get(names.size() - 1) + DESCRIPTIONS[8] + DESCRIPTIONS[9];
        }
        if (counter == 1) {
            return DESCRIPTIONS[5] + names.get(0) + DESCRIPTIONS[8] + DESCRIPTIONS[9];
        }
        return "Uh oh! Something broke.";
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicFerryPass();
    }
}
