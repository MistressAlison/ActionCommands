package ActionCommands.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WordPopupEffect extends AbstractGameEffect {
    private static final float TEXT_DURATION = 1F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private static final float LERP_RATE = 5.0F;
    private float x;
    private float y;
    private float offsetY;
    private String msg;
    private Color targetColor;
    private float angle = -5;

    public WordPopupEffect(float x, float y, String msg, Color targetColor) {
        this.duration = TEXT_DURATION;
        this.startingDuration = TEXT_DURATION;
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.targetColor = targetColor;
        this.color = Color.WHITE.cpy();
        this.offsetY = STARTING_OFFSET_Y;
    }

    public void update() {
        super.update();
        this.color.r = Interpolation.exp5In.apply(this.targetColor.r, 1.0F, this.duration / this.startingDuration);
        this.color.g = Interpolation.exp5In.apply(this.targetColor.g, 1.0F, this.duration / this.startingDuration);
        this.color.b = Interpolation.exp5In.apply(this.targetColor.b, 1.0F, this.duration / this.startingDuration);
        this.offsetY = MathUtils.lerp(this.offsetY, TARGET_OFFSET_Y, Gdx.graphics.getDeltaTime() * LERP_RATE);
        this.y += Gdx.graphics.getDeltaTime() * 42.0F * Settings.scale;
        this.x += Gdx.graphics.getDeltaTime() * 10.0F * Settings.scale;
        this.angle -= Gdx.graphics.getDeltaTime() * 2.0F;
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            //FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, this.msg, this.x, this.y + this.offsetY, this.color, 1.2F);
            FontHelper.renderRotatedText(sb, FontHelper.bannerNameFont, msg, x, y, 0, offsetY, angle, true, color);
        }

    }

    public void dispose() {
    }

    static {
        STARTING_OFFSET_Y = 80.0F * Settings.scale;
        TARGET_OFFSET_Y = 120.0F * Settings.scale;
    }
}
