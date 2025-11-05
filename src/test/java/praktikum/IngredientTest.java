package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class IngredientTest {
    private final IngredientType type;
    private final String name;
    private final float price;
    private Ingredient ingredient;

    public IngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "Тестовые данные: type={0}, name={1}, price={2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {IngredientType.SAUCE, "Соус традиционный галактический", 15.5f},
                {IngredientType.SAUCE, "Соус фирменный космический", 20.0f},
                {IngredientType.FILLING, "Говяжий метеорит", 30.0f},
                {IngredientType.FILLING, "Сыр с астероидной плесенью", 25.5f},
                {IngredientType.FILLING, "Хрустящие минеральные кольца", 18.75f},
                {IngredientType.SAUCE, "", 0.0f},
                {IngredientType.FILLING, "минимальная цена", 0.01f},
                {IngredientType.SAUCE, "максимальная цена", Float.MAX_VALUE}
        };
    }


    @Before
    public void setUp(){
        ingredient = new Ingredient(type, name, price);
    }

    @Test
    public void TestGetPrice() {
        assertEquals("Цена ингредиента должна совпадать", price, ingredient.getPrice(), 0.001f);
    }

    @Test
    public void TestGetName() {
        assertEquals("Имя ингредиента должна совпадать", name, ingredient.getName());
    }

    @Test
    public void TestGetType() {
        assertEquals("Тип ингредиента должна совпадать", type, ingredient.getType());
    }
}