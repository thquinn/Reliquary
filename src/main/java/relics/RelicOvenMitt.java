package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicOvenMitt extends CustomRelic {
    public static final String ID = "reliquary:OvenMitt";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/ovenMitt.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/ovenMitt.png");

    static int USES = 5;

    public RelicOvenMitt() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = USES;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount <= 0 || counter == 0) {
            return damageAmount;
        }
        flash();
        setCounter(counter - 1);
        return 0;
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
        if (counter <= 0) {
            usedUp();
            description = DESCRIPTIONS[3];
        } else {
            description = counter == 1 ? DESCRIPTIONS[2] : DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + USES + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicOvenMitt();
    }
}
