package gov.usgs.earthquake.event;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

/**
 * JSON Event class.
 */
public class JSONEvent {

	private EventID eventID;
	private JSONObject json;

	// These are based off of the feed v1.0 GeoJSON features
	private static String [] values = {

		// doubles
		"mag", "cdi", "mmi", "dmin", "rms", "gap",
		"longitude", "latitude", "depth",

		// strings
		"place", "url", "detail", "alert", "status",
		"net", "code", "ids", "sources", "types", "magType",

		// integers
		"time", "updated", "tz", "felt", "tsunami", "sig", "nst"
	};

	/**
	 * Construct a new JSONEvent from an existing json object.
	 *
	 * @param json
	 */
	public JSONEvent(JSONObject json) {
		this.json = new JSONObject(json);
		eventID = new EventID(this.getNet(), this.getCode());
	}

	// Getters
	public EventID getEventID()			{return eventID;}

	public BigDecimal getMag()			{return new BigDecimal((String) json.get("mag"));}
	public BigDecimal getCdi()			{return new BigDecimal((String) json.get("cdi"));}
	public BigDecimal getMmi()			{return new BigDecimal((String) json.get("mmi"));}
	public BigDecimal getDmin()			{return new BigDecimal((String) json.get("dmin"));}
	public BigDecimal getRms()			{return new BigDecimal((String) json.get("rms"));}
	public BigDecimal getGap()			{return new BigDecimal((String) json.get("gap"));}
	public BigDecimal getLongitude()	{return new BigDecimal((String) json.get("longitude"));}
	public BigDecimal getLatitude()		{return new BigDecimal((String) json.get("latitude"));}
	public BigDecimal getDepth()		{return new BigDecimal((String) json.get("depth"));}

	public String getPlace()			{return (String) json.get("place");}
	public String getUrl()				{return (String) json.get("url");}
	public String getDetail()			{return (String) json.get("detail");}
	public String getAlert()			{return (String) json.get("alert");}
	public String getStatus()			{return (String) json.get("status");}
	public String getNet()				{return (String) json.get("net");}
	public String getCode()				{return (String) json.get("code");}
	public String getIds()				{return (String) json.get("ids");}
	public String getSources()			{return (String) json.get("sources");}
	public String getTypes()			{return (String) json.get("types");}
	public String getMagType()			{return (String) json.get("magType");}

	public int getTime()				{return Integer.parseInt((String) json.get("time"));}
	public int getUpdated()				{return Integer.parseInt((String) json.get("updated"));}
	public int getTz()					{return Integer.parseInt((String) json.get("tz"));}
	public int getFelt()				{return Integer.parseInt((String) json.get("felt"));}
	public int getTsunami()				{return Integer.parseInt((String) json.get("tsunami"));}
	public int getSig()					{return Integer.parseInt((String) json.get("sig"));}
	public int getNst()					{return Integer.parseInt((String) json.get("nst"));}
}
