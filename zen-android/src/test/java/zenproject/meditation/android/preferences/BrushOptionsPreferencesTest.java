package zenproject.meditation.android.preferences;

import android.content.SharedPreferences;
import android.graphics.Color;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import zenproject.meditation.android.ZenTestBase;
import zenproject.meditation.android.sketch.painting.ink.BrushColor;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BrushOptionsPreferencesTest extends ZenTestBase {
    private static final String PREF_NAME = "BrushPreferences";
    private static final String PREF_BRUSH_COLOR = PREF_NAME + "Color";
    private static final String PREF_BRUSH_SIZE = PREF_NAME + "Size";
    private static final int ANY_PERCENTAGE = 10;
    private static final BrushColor ANY_COLOR = BrushColor.ACCENT;
    private static final int LEAF_COLOR = Color.rgb(59, 158, 58);

    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;

    private BrushOptionsPreferences brushOptionsPreferences;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putInt(anyString(), anyInt())).thenReturn(editor);

        brushOptionsPreferences = new BrushOptionsPreferences(sharedPreferences);
    }

    @Test
    public void testThatApplyBrushSizePutsValuesInEditor() {
        brushOptionsPreferences.applyBrushSizePercentage(ANY_PERCENTAGE);

        verify(editor).putInt(PREF_BRUSH_SIZE, ANY_PERCENTAGE);
    }

    @Test
    public void testThatGetBrushSizeReturnsGetsValueFromSharedPreferences() {
        givenThatSizeWasApplied();

        assertThat(brushOptionsPreferences.getBrushSizePercentage()).isEqualTo(ANY_PERCENTAGE);
    }

    private void givenThatSizeWasApplied() {
        when(sharedPreferences.getInt(eq(PREF_BRUSH_SIZE), anyInt())).thenReturn(ANY_PERCENTAGE);
    }

    @Test
    public void testThatApplyBrushSizeAppliesChanges() {
        brushOptionsPreferences.applyBrushSizePercentage(ANY_PERCENTAGE);

        verify(editor).apply();
    }

    @Test
    public void testThatGetBrushColorReturnsGetsValueFromSharedPreferences() {
        givenThatColorWasApplied();

        assertThat(brushOptionsPreferences.getBrushColor()).isEqualTo(ANY_COLOR);
    }

    @Test
    public void testThatGetBranchColorReturnsGetsValueFromSharedPreferences() {
        brushOptionsPreferences.getBranchColor();

        verify(sharedPreferences).getInt(eq(PREF_BRUSH_COLOR), anyInt());
    }

    private void givenThatColorWasApplied() {
        when(sharedPreferences.getInt(eq(PREF_BRUSH_COLOR), anyInt())).thenReturn(BrushColor.ACCENT.toAndroidColor());
    }

    @Test
    public void testThatApplyBrushColorPutsValuesInEditor() {
        brushOptionsPreferences.applyBrushColor(ANY_COLOR);

        verify(editor).putInt(PREF_BRUSH_COLOR, ANY_COLOR.toAndroidColor());
    }

    @Test
    public void testThatApplyBrushColorAppliesChanges() {
        brushOptionsPreferences.applyBrushSizePercentage(ANY_PERCENTAGE);

        verify(editor).apply();
    }

    @Test
    public void testThatGetLeafColorReturnsCorrectColor() {
        assertThat(brushOptionsPreferences.getLeafColor()).isEqualTo(LEAF_COLOR);
    }

    @Test
    public void testThatNewInstanceReturnsNotNullBrushOptionsPreferences() {
        assertThat(BrushOptionsPreferences.newInstance()).isNotNull();
    }

    @Test
    public void testThatNewInstanceReturnsANewInstance() {
        BrushOptionsPreferences firstInstance = BrushOptionsPreferences.newInstance();
        BrushOptionsPreferences secondInstance = BrushOptionsPreferences.newInstance();

        assertThat(firstInstance).isNotEqualTo(secondInstance);
    }
}
