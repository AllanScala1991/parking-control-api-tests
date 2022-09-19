package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import test.CreateParkingTest;
import test.DeletedParkingTest;
import test.FindParkingTest;
import test.UpdateParkingTest;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
        CreateParkingTest.class,
        FindParkingTest.class,
        UpdateParkingTest.class,
        DeletedParkingTest.class
})

public class Suite {}
