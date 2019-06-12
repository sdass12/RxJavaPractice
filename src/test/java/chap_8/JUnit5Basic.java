package chap_8;

import common.Log;
import common.Shape;
import io.reactivex.Observable;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
public class JUnit5Basic {

   @Test
   @DisplayName("JUnit 5 First Example")
    public void testFirst(){
        int expected = 3;
        int actual = 1 + 2;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test getShape() Observable")
    public void testGetShapeObservable(){
        String[] data = {"1", "2-R", "3-T"};
        Observable<String> source = Observable.fromArray(data)
                .map(Shape::getShape);

        String[] expected = {Shape.BALL, Shape.RECTANGLE, Shape.TRIANGLE};
        List<String> actual = new ArrayList<>();
        source.doOnNext(Log::d)
                .subscribe(actual::add);

        assertEquals(Arrays.asList(expected), actual);
    }
}
