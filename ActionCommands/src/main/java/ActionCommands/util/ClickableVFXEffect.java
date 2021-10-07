package ActionCommands.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public abstract class ClickableVFXEffect extends AbstractGameEffect {

    public Hitbox hitbox;
    protected boolean clickable;
    protected float x, y;
    protected final TextureAtlas.AtlasRegion img;

    public ClickableVFXEffect(TextureAtlas.AtlasRegion img, float x, float y) {
        this(img, x, y, 1, 0);
    }

    public ClickableVFXEffect(TextureAtlas.AtlasRegion img, float x, float y, float scale, float rotation) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.scale = scale;
        this.rotation = rotation;
    }

    @Override
    public void update() {
        hitbox.move(x, y);
        super.update();
        this.updateHitbox();
        if (this.hitbox.hovered) {
            this.onHover();
        } else {
            this.onUnhover();
        }

        if (this.hitbox.hovered && InputHelper.justClickedLeft && this.clickable) {
            this.onClick();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {

    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    protected abstract void onHover();

    protected abstract void onUnhover();

    protected abstract void onClick();

    protected void updateHitbox() {
        this.hitbox.update();
    }

}
