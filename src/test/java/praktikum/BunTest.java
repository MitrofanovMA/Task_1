package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BunTest {
    private final String name;
    private final float price;
    private Bun bun;


    public BunTest(String name, float price) {
        this.name = name;
        this.price = price;
    }


    @Parameterized.Parameters(name = "Тестовые данные: name={0}, price={1}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Обычная булочка", 100.0f},
                {"Специальная булочка", 150.5f},
                {"", 0.0f}, // граничный случай: пустое название
                {"Булочка с очень длинным названием которое может быть использовано в системе", 999.99f},
                {"Булочка", -50.0f}, // граничный случай: отрицательная цена
                {null, 75.0f} // граничный случай: null название
        };
    }

    @Before
    public void setUp() {
        bun = new Bun(name, price);
    }

    @Test
    public void testGetName() {
        assertEquals("Название булочки должно совпадать с переданным в конструктор", name, bun.getName());

    }

    @Test
    public void testGetPrice() {
        assertEquals("Цена булочки должна совпадать с переданной в конструктор", price, bun.getPrice(), 0.001f);
    }
}