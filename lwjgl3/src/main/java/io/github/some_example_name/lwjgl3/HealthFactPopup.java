package io.github.some_example_name.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public class HealthFactPopup {
    private static final String[] FACTS = {
        "Did you know? Carrots are rich in Vitamin A which helps your vision!",
        "Instead of cake, try a banana for a sweet and healthy treat.",
        "Too much sugar increases your risk of heart disease.",
        "Try swapping soda for fresh fruit juice or water!",
        "Blueberries are loaded with antioxidants — great for your brain!"
    };

    private boolean isVisible = false;
    private String currentFact;
    private Texture background;
    private Texture closeButton;
    private Rectangle closeBounds;

    private final int popupWidth = 600;
    private final int popupHeight = 200;
    private final int closeSize = 36; // slightly smaller for better fit

    public HealthFactPopup() {
        background = new Texture("popups/fact_bg.png");
        closeButton = new Texture("popups/x_button.png");
        closeBounds = new Rectangle(0, 0, closeSize, closeSize);
    }

    public void showRandomFact() {
        currentFact = FACTS[new Random().nextInt(FACTS.length)];
        isVisible = true;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        if (!isVisible) return;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float popupWidth = 600f;
        float popupHeight = 200f;
        float popupX = (screenWidth - popupWidth) / 2f;
        float popupY = (screenHeight - popupHeight) / 2f;

        float margin = 35f; // ✅ Best for close button and consistent margin
        float closeSize = 35f;

        // ✅ Proper positioning of the close button inside the popup
        closeBounds.set(
            popupX + popupWidth - closeSize - margin,
            popupY + popupHeight - closeSize - margin,
            closeSize,
            closeSize
        );

        batch.draw(background, popupX, popupY, popupWidth, popupHeight);

        // ✅ Center text vertically and use correct wrap width
        font.getData().setScale(1.4f);
        float textX = popupX + margin;
        float textY = popupY + popupHeight / 2f + 20f;
        float wrapWidth = popupWidth - 2 * margin;

        font.draw(batch, currentFact, textX, textY, wrapWidth, Align.center, true);

        // ✅ Draw close button inside
        batch.draw(closeButton, closeBounds.x, closeBounds.y, closeSize, closeSize);
    }


    public void handleInput(int screenX, int screenY) {
        if (isVisible) {
            float flippedY = Gdx.graphics.getHeight() - screenY;
            if (closeBounds.contains(screenX, flippedY)) {
                isVisible = false;
            }
        }
    }

    public boolean isVisible() {
        return isVisible;
    }
}
