package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;

public abstract class ReliquaryRelic extends CustomRelic {
    RelicType relicType;

    public ReliquaryRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        this(id, texture, outline, tier, sfx, RelicType.SHARED);
    }
    public ReliquaryRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, RelicType relicType) {
        super(id, texture, outline, tier, sfx);
        this.relicType = relicType;
    }

    public RelicType getRelicType() {
        return relicType;
    }
    public boolean isRetired() {
        return false;
    }
}
