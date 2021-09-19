package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import util.TextureLoader;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RelicElizabethanCollar extends CustomRelic {
    public static final String ID = "reliquary:ElizabethanCollar";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/elizabethanCollar.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/elizabethanCollar.png");

    public RelicElizabethanCollar() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        AbstractPlayer p = AbstractDungeon.player;
        Set<String> purgeableIDs = getCurseIDsWorseThanInjury();
        return p.masterDeck.group.stream().anyMatch(c -> purgeableIDs.contains(c.cardID));
    }

    @Override
    public void onEquip() {
        replaceCurses();
    }
    @Override
    public void onMasterDeckChange() {
        replaceCurses();
    }

    void replaceCurses() {
        AbstractPlayer p = AbstractDungeon.player;
        List<AbstractCard> deck = p.masterDeck.group;
        Set<String> purgeableIDs = getCurseIDsWorseThanInjury();
        for (int i = 0; i < deck.size(); i++) {
            AbstractCard card = deck.get(i);
            if (purgeableIDs.contains(card.cardID)) {
                deck.remove(i);
                AbstractCard injury = new Injury();
                p.masterDeck.addToTop(injury);
                UnlockTracker.markCardAsSeen(Injury.ID);
                injury.isSeen = true;
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(injury);
                }
                i--;
                flash();
            }
        }
    }

    Set<String> getCurseIDsWorseThanInjury() {
        Set<String> purgeableIDs = AbstractDungeon.player.masterDeck.getPurgeableCards().group.stream().filter(c -> c.type == AbstractCard.CardType.CURSE).map(c -> c.cardID).collect(Collectors.toSet());
        purgeableIDs.remove(Injury.ID);
        purgeableIDs.remove(Clumsy.ID);
        purgeableIDs.remove(Parasite.ID);
        return purgeableIDs;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicElizabethanCollar();
    }
}
