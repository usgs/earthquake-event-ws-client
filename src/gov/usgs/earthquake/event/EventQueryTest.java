package gov.usgs.earthquake.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

// TODO :: Finish these tests !!

public class EventQueryTest {

  private EventQuery query = null;

  @Before
  public void setup () {
    query = new EventQuery();
  }

  @Test
  public void testStartTime () {
    long startTime = 0x12345L;
    query.setStartTime(startTime);
    Assert.assertEquals(startTime, query.getStartTime());
  }

  @Test
  public void testEndTime () {
    long endTime = 0x23456L;
    query.setEndTime(endTime);
    Assert.assertEquals(endTime, query.getEndTime());
  }

  @Test
  public void testUpdatedAfter () {
    long updatedAfter = 0x34567L;
    query.setUpdatedAfter(updatedAfter);
    Assert.assertEquals(updatedAfter, query.getUpdatedAfter());
  }


  @Test
  public void testGoodOrderBy () {
    String orderby = "time-asc";
    query.setOrderBy(orderby);
    Assert.assertEquals(orderby, query.getOrderBy());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testBadOrderBy () {
    String orderby = "depth";
    query.setOrderBy(orderby);
    Assert.assertEquals(orderby, query.getOrderBy());
  }

  @Test
  public void testGoodAlertLevel () {
    String red = "red";
    query.setAlertLevel(red);
    Assert.assertEquals(red, query.getAlertLevel());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testBadAlertLevel () {
    String blue = "blue";
    query.setAlertLevel(blue);
    Assert.assertEquals(blue, query.getAlertLevel());
  }
}
