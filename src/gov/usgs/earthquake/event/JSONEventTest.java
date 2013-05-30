package gov.usgs.earthquake.event;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

// TODO :: Finish these tests !!

public class JSONEventTest {

  private JSONObject json = null;


  @Before
  public void setup () {
    json = new JSONObject();
  }

  @Test
  public void testEventId () {
    String network = "network";
    String code = "code";
    EventID id = new EventID(network, code);

    json.put("net", network);
    json.put("code", code);
    JSONEvent event = new JSONEvent(json);

    // TODO :: Maybe need an EventID.equals method?
    Assert.assertEquals(id.toString(), event.getEventID().toString());
    Assert.assertEquals(id.getNetwork(), event.getNet());
    Assert.assertEquals(id.getCode(), event.getCode());
  }

  @Test
  public void testMag () {
    String mag = "4.5";

    json.put("mag", mag);
    JSONEvent event = new JSONEvent(json);

    Assert.assertEquals(new BigDecimal(mag), event.getMag());
  }

  @Test
  public void testCdi () {
    String cdi = "4.25";

    json.put("cdi", cdi);
    JSONEvent event = new JSONEvent(json);

    Assert.assertEquals(new BigDecimal(cdi), event.getCdi());
  }

  @Test
  public void testMmi () {
    String mmi = "5.5";

    json.put("mmi", mmi);
    JSONEvent event = new JSONEvent(json);

    Assert.assertEquals(new BigDecimal(mmi), event.getMmi());
  }
}
