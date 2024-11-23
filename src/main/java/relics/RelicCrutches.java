package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import util.TextureLoader;

import java.util.stream.Stream;

public class RelicCrutches extends ReliquaryRelic {
    public static final String ID = "reliquary:Crutches";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/crutches.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/crutches.png");

    static int STRENGTH_LOSS = 1;

    int lastStatusCount;

    public RelicCrutches() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        lastStatusCount = statusCount();
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.actionManager.turnHasEnded)
            return;
        int statusCount = statusCount();
        if (statusCount > lastStatusCount) {
            AbstractPlayer p = AbstractDungeon.player;
            int amount = statusCount - lastStatusCount;
            addToBot(new RelicAboveCreatureAction(p, this));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amount)));
                if (!mo.hasPower(ArtifactPower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, amount)));
                }
            }
        }
        lastStatusCount = statusCount;
    }

    static int statusCount() {
        AbstractPlayer p = AbstractDungeon.player;
        return (int) Stream.of(p.hand.group.stream(),
                               p.drawPile.group.stream(),
                               p.discardPile.group.stream(),
                               p.limbo.group.stream()).flatMap(i -> i).filter(c -> c.type == AbstractCard.CardType.STATUS).count();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH_LOSS + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicCrutches();
    }
}
