package praktikum;

import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientTypeTest {

    @Test
    public void TestValues() {
        IngredientType[] values = IngredientType.values();
        assertNotNull(values);
    }

    @Test
    public void TestValueOf() {
        assertEquals(IngredientType.SAUCE, IngredientType.valueOf("SAUCE"));
        assertEquals(IngredientType.FILLING, IngredientType.valueOf("FILLING"));

    }
}