import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RestaurantTest{

    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    @Spy
    Restaurant restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime, closingTime);
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void beforeEachTest(){

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime timeBetweenOpenAndClose = LocalTime.parse("21:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(timeBetweenOpenAndClose);
        assertTrue(restaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime closedTime = LocalTime.parse("23:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(closedTime);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<<<SELECT VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void total_cost_increases_when_an_item_is_selected() {

        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        assertEquals(388, restaurant.getOrderTotal(selectedItems));
    }

    @Test
    public void total_cost_decrease_when_an_item_is_deselected(){
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        selectedItems.remove("Vegetable lasagne");
        assertEquals(119,restaurant.getOrderTotal(selectedItems));

    }
    @Test
    public void total_cost_should_be_zero_if_no_item_selected(){
        List<String> selectedItems = new ArrayList<String>();
        assertEquals(0,restaurant.getOrderTotal(selectedItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<SELECT VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}