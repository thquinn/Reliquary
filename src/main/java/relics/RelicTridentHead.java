package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

public class RelicTridentHead extends CustomRelic {
    public static final String ID = "reliquary:TridentHead";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/tridentHead.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/tridentHead.png");

    private static boolean usedThisCombat = false;

    public RelicTridentHead() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
    }

    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        if (AreThereMultipleEnemies()) {
            pulse = true;
            beginPulse();
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!AreThereMultipleEnemies() || usedThisCombat || c.target != AbstractCard.CardTarget.ENEMY) {
            return;
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
            AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if (mo == m) continue;
            if (mo.isDeadOrEscaped()) continue;
            AbstractCard copy = c.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(copy);
            copy.current_x = c.current_x;
            copy.current_y = c.current_y;
            copy.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            copy.target_y = Settings.HEIGHT / 2.0F;
            copy.calculateCardDamage(mo);
            copy.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy, mo, copy.energyOnUse, true, true), true);
        }
        usedThisCombat = true;
        flash();
        pulse = false;
        grayscale = true;
    }

    @Override
    public void onVictory() {
        pulse = false;
    }

    boolean AreThereMultipleEnemies() {
        return AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(mo -> !mo.isDeadOrEscaped()).count() > 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicTridentHead();
    }
}
