package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;
import util.TextureLoader;

public class RelicRustamsPendant extends CustomRelic {
    public static final String ID = "reliquary:RustamsPendant";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/rustamsPendant.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/rustamsPendant.png");

    static int STRENGTH_LOSS = 1;
    static int DEXTERITY_LOSS = 1;
    static int FOCUS_LOSS = 1;
    static int DEVOTION_LOSS = 3;
    static int ATONEMENT_THRESHOLD = 10;

    public RelicRustamsPendant() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public boolean canSpawn() {
        AbstractPlayer p = AbstractDungeon.player;
        return p.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD ||
               p.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT ||
               p.chosenClass == AbstractPlayer.PlayerClass.DEFECT ||
               p.chosenClass == AbstractPlayer.PlayerClass.WATCHER;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        if (p.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -STRENGTH_LOSS)));
        } else if (p.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -DEXTERITY_LOSS)));
        } else if (p.chosenClass == AbstractPlayer.PlayerClass.DEFECT) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, -FOCUS_LOSS)));
        } else if (p.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
            addToBot(new ApplyPowerAction(p, p, new DevotionPower(p, -DEVOTION_LOSS)));
        } else {
            ReliquaryLogger.error("Unknown class triggered Rustam's Pendant.");
        }
    }

    @Override
    public String getUpdatedDescription() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return DESCRIPTIONS[0];
        }
        if (p.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) {
            return DESCRIPTIONS[1] + -STRENGTH_LOSS + DESCRIPTIONS[2];
        } else if (p.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            return DESCRIPTIONS[1] + -DEXTERITY_LOSS + DESCRIPTIONS[3];
        } else if (p.chosenClass == AbstractPlayer.PlayerClass.DEFECT) {
            return DESCRIPTIONS[1] + -FOCUS_LOSS + DESCRIPTIONS[4];
        } else if (p.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
            return DESCRIPTIONS[1] + -DEVOTION_LOSS + DESCRIPTIONS[5] + -ATONEMENT_THRESHOLD + DESCRIPTIONS[6];
        }
        ReliquaryLogger.error("Unknown class seeing Rustam's Pendant.");
        return "ERROR";
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRustamsPendant();
    }
}
