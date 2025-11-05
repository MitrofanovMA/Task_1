package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredient1;
    @Mock
    private Ingredient mockIngredient2;

    private Burger burger;

    private final float bunPrice;
    private final float ingredientPrice;
    private final float expectedTotal;

    public BurgerTest(float bunPrice, float ingredientPrice, float expectedTotal) {
        this.bunPrice = bunPrice;
        this.ingredientPrice = ingredientPrice;
        this.expectedTotal = expectedTotal;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0.0f, 0.0f, 0.0f},
                {100.0f, 50.0f, 250.0f},
                {200.0f, 150.0f, 550.0f}
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }

    @Test
    public void TestSetBuns() {
        Mockito.when(mockBun.getPrice()).thenReturn(bunPrice);
        Mockito.when(mockBun.getName()).thenReturn("mock_bun");
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }
    @Test
    public void TestSetNullBuns() {
        burger.setBuns(null);
        assertNull(burger.bun);
    }

    @Test
    public void TestAddIngredient() {
        Mockito.when(mockIngredient1.getPrice()).thenReturn(ingredientPrice);
        burger.addIngredient(mockIngredient1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
    }


    @Test
    public void TestRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient2, burger.ingredients.get(0));
    }
    @Test
    public void TestNegativeRemoveNullIngredient() {
        burger.addIngredient(mockIngredient1);

        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    //Negative tests for remove ingredient
    @Test(expected = IndexOutOfBoundsException.class)
    public void TestRemoveFromEmptyListThrowsException() {
        burger.removeIngredient(0);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void TestRemoveWithNegativeIndexThrowsException() {
        burger.addIngredient(mockIngredient1);
        burger.removeIngredient(-1);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void TestRemoveWithIndexOutOfBoundsThrowsException() {
        burger.addIngredient(mockIngredient1);
        burger.removeIngredient(1);
    }


    @Test
    public void TestMoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
        burger.moveIngredient(0,1);
        assertEquals(mockIngredient1, burger.ingredients.get(1));
        assertEquals(mockIngredient2, burger.ingredients.get(0));
    }

    //Negative tests for move ingredient
    @Test(expected = IndexOutOfBoundsException.class)
    public void TestMoveIngredientWithInvalidIndices() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.moveIngredient(-1, 0); // отрицательный индекс
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void TestMoveIngredientToOutOfBoundsIndex() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.moveIngredient(0, 10); // целевой индекс вне границ
    }

    @Test
    public void TesGetPrice() {
        Mockito.when(mockIngredient1.getPrice()).thenReturn(ingredientPrice);
        Mockito.when(mockBun.getPrice()).thenReturn(bunPrice);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        assertEquals(expectedTotal, burger.getPrice(), 0.001f);
    }

    @Test
    public void TestGetReceipt() {
        Mockito.when(mockBun.getName()).thenReturn("mock_bun");
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);
        Mockito.when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(mockIngredient1.getName()).thenReturn("mock_SAUCE");
        Mockito.when(mockIngredient1.getPrice()).thenReturn(300.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== mock_bun ====)"));
        assertTrue(receipt.contains("= sauce mock_SAUCE ="));
        assertTrue(receipt.contains("Price: 500.0"));
    }
    @Test
    public void TestGetReceiptWithNoIngredients() {
        Mockito.when(mockBun.getName()).thenReturn("mock_bun");
        Mockito.when(mockBun.getPrice()).thenReturn(100.0f);

        burger.setBuns(mockBun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== mock_bun ====)"));
        assertTrue(receipt.contains("Price: 200.0")); // true
        assertFalse(receipt.contains("sauce")); // false
    }

}