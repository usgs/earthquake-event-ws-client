package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import gov.usgs.earthquake.event.EventID;

/**
 * JSON Event class.
 */
public class JSONEvent {

	private EventID eventID;
	private JSONObject json;

	// These are based off of the feed v1.0 GeoJSON features
	private static values = [

		// doubles
		"mag", "cdi", "mmi", "dmin", "rms", "gap",
		"longitude", "latitude", "depth",

		// strings
		"place", "url", "detail", "alert", "status",
		"net", "code", "ids", "sources", "types", "magType",

		// integers
		"time", "updated", "tz", "felt", "tsunami", "sig", "nst"
	];

	/**
	 * Construct a new JSONEvent with an empty json.
	 */
	public JSONEvent() {
		json = new JSONObject();
	}

	/**
	 * Construct a new JSONEvent from an existing json object.
	 * 
	 * @param [JSONObject] json
	 */
	public JSONEvent(JSONObject json) {
		this.json = new JSONObject(json, values);
		eventID = new EventID(this.json.getNetwork(), this.json.getCode());
	}

	// Getters
	public EventID getEventID()			{return eventID};

	public BigDecimal getMag()			{return new BigDecimal(json.get("mag"));}
	public BigDecimal getCdi()			{return new BigDecimal(json.get("cdi"));}
	public BigDecimal getMmi()			{return new BigDecimal(json.get("mmi"));}
	public BigDecimal getDmin()			{return new BigDecimal(json.get("dmin"));}
	public BigDecimal getRms()			{return new BigDecimal(json.get("rms"));}
	public BigDecimal getGap()			{return new BigDecimal(json.get("gap"));}
	public BigDecimal getLongitude()	{return new BigDecimal(json.get("longitude"));}
	public BigDecimal getLatitude()		{return new BigDecimal(json.get("latitude"));}
	public BigDecimal getDepth()		{return new BigDecimal(json.get("depth"));}

	public String getPlace()			{return json.get("place");}
	public String getUrl()				{return json.get("url");}
	public String getDetail()			{return json.get("detail");}
	public String getAlert()			{return json.get("alert");}
	public String getStatus()			{return json.get("status");}
	public String getNet()				{return json.get("net");}
	public String getCode()				{return json.get("code");}
	public String getIds()				{return json.get("ids");}
	public String getSources()			{return json.get("sources");}
	public String getTypes()			{return json.get("types");}
	public String getMagType()			{return json.get("magType");}

	public int getTime()				{return json.get("time");}
	public int getUpdated()				{return json.get("updated");}
	public int getTz()					{return json.get("tz");}
	public int getFelt()				{return json.get("felt");}
	public int getTsunami()				{return json.get("tsunami");}
	public int getSig()					{return json.get("sig");}
	public int getNst()					{return json.get("nst");}