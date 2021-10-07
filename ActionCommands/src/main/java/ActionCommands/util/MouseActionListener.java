package ActionCommands.util;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.util.ArrayList;

public class MouseActionListener {
    public static final ArrayList<MouseClickObject> mouseClicks = new ArrayList<>();
    private static boolean leftDown;
    private static boolean rightDown;
    private static final float DROP_OFF_FRAME = 30/60f;

    @SpirePatch(clz = InputHelper.class, method = "updateFirst")
    public static class InputGrabber {
        @SpirePostfixPatch
        public static void withoutCrashingHopefully() {
            if (InputHelper.justClickedLeft) {
                leftDown = true;
            }
            if (leftDown && InputHelper.justReleasedClickLeft && !InputHelper.justReleasedClickRight) {
                leftDown = false;
                mouseClicks.add(new MouseClickObject(true));
            }
            if (InputHelper.justClickedRight) {
                rightDown = true;
            }
            if (rightDown && InputHelper.justReleasedClickRight) {
                rightDown = false;
                mouseClicks.add(new MouseClickObject(false));
            }
            updateTimers();
        }
    }

    public static void purgeClickList() {
        mouseClicks.clear();
    }

    public static boolean allOthersSuccessful() {
        boolean skip = true;
        for (MouseClickObject o : mouseClicks) {
            if (!skip) {
                if (!o.successfulBlock) {
                    return false;
                }
            }
            skip = false;
        }
        return true;
    }

    public static MouseClickObject getLatestClick() {
        if (mouseClicks.size() > 0) {
            return mouseClicks.get(0);
        }
        return null;
    }

    public static boolean clickedInFrameLimit(int frames) {
        if (getLatestClick() != null) {
            return getLatestClick().timer <= frames/60f;
        }
        return false;
    }

    public static boolean clickedInFrameLimit(int leftClickFrames, int rightClickFrames) {
        if (getLatestClick() != null) {
            if (getLatestClick().leftClick) {
                return getLatestClick().timer <= leftClickFrames/60f;
            }
            return getLatestClick().timer <= rightClickFrames/60f;
        }
        return false;
    }

    public static void setClickSuccessful() {
        if (getLatestClick() != null) {
            getLatestClick().successfulBlock = true;
        }
    }

    private static void updateTimers() {
        for (MouseClickObject o : mouseClicks) {
            o.updateTimer();
        }
        mouseClicks.removeIf(o -> o.timer >= DROP_OFF_FRAME);
    }

    public static int calculateTimingIndex(int frameWindow, int textOptions) {
        if (getLatestClick() != null) {
            float checkDuration = getLatestClick().timer;
            int steps = (2*textOptions) - 1;
            float step = (frameWindow/60f)/steps;
            float t = 0;
            int i = 0;
            while (i < steps) {
                t += step;
                if (t >= checkDuration) {
                    break;
                }
                i++;
            }
            if (i < textOptions) {
                return i;
            } else {
                return (2*(textOptions-1)) - i;
            }
        }
        return -1; // There were no clicks
    }


    public static class MouseClickObject {
        float timer;
        boolean leftClick;
        boolean successfulBlock;

        public MouseClickObject(boolean leftClick) {
            this.leftClick = leftClick;
        }

        public void updateTimer() {
            timer += Gdx.graphics.getDeltaTime();
        }

        public boolean isLeftClick() {
            return leftClick;
        }
    }
}
