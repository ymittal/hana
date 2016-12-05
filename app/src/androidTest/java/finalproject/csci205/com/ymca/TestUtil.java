package finalproject.csci205.com.ymca;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;

/**
 * Utility class containing custom {@link android.support.test.espresso.matcher.ViewMatchers}
 */
public class TestUtil {

    /**
     * A custom ViewMatcher for views with errors which are not supported by the library
     *
     * @param stringMatcher {@link Matcher<String>} containing error string of {@link TextInputLayout}
     * @return {@link Matcher<View>} object
     * @see <a href="http://stackoverflow.com/questions/37073050/how-can-you-check-a-textinputlayouts-error-with-espresso">
     * Stack Overflow - How can you check a TextinputLayout's error with Espresso</a>
     */
    public static Matcher<View> withErrorInInputLayout(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);

        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            String actualError = "";

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(TextInputLayout textInputLayout) {
                CharSequence error = textInputLayout.getError();
                if (error != null) {
                    actualError = error.toString();
                    return stringMatcher.matches(actualError);
                }
                return false;
            }
        };
    }

    /**
     * @param resourceId background resource Id of {@link View}
     * @return true if view has background with resource Id as <code>resourceId</code>
     * @see <a href="https://gist.github.com/frankiesardo/7490059">
     * GitHub - Espresso & Brioche</a>
     */
    public static Matcher<View> withBackground(final int resourceId) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                return sameBitmap(view.getContext(), view.getBackground(), resourceId);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has background resource " + resourceId);
            }
        };
    }

    /**
     * Returns true of <code>drawable</code>'s {@link Bitmap} is the same as that of the
     * one with <code>resourceId</code>
     *
     * @param context    view {@link Context}
     * @param drawable   {@link Drawable} object
     * @param resourceId resource Id of {@link Drawable}
     * @return true if {@link Bitmap} matches
     */
    private static boolean sameBitmap(Context context, Drawable drawable, int resourceId) {
        Drawable otherDrawable = ContextCompat.getDrawable(context, resourceId);
        if (drawable == null || otherDrawable == null) {
            return false;
        }
        if (drawable instanceof StateListDrawable && otherDrawable instanceof StateListDrawable) {
            drawable = drawable.getCurrent();
            otherDrawable = otherDrawable.getCurrent();
        }
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap otherBitmap = ((BitmapDrawable) otherDrawable).getBitmap();
            return bitmap.sameAs(otherBitmap);
        }
        return false;
    }
}
