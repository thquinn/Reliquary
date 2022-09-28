package relics;

import basemod.abstracts.CustomRelic;
import basemod.devcommands.relic.Relic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import util.TextureLoader;

public class RelicBloodSugar extends CustomRelic {
    public static final String ID = "reliquary:BloodSugar";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/bloodSugar.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/bloodSugar.png");

    private static final int INITIAL_HEAL = 7;

    public RelicBloodSugar() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = INITIAL_HEAL;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        if (usedUp || counter <= 0) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        p.heal(counter);
        setCounter(counter - 1);
    }
    @Override
    public void onRest() {
        flash();
        setCounter(INITIAL_HEAL);
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if (!isObtained) {
            return DESCRIPTIONS[0] + INITIAL_HEAL + DESCRIPTIONS[1] + INITIAL_HEAL + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0] + counter + DESCRIPTIONS[1] + INITIAL_HEAL + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBloodSugar();
    }
}
