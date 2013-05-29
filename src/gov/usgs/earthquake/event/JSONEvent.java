package gov.usgs.earthquake.event;

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
	}

	// Setters
	public void setEventID(String network, String code) {
		eventID = new EventID(network, code);
	}

	public void setMag(double in)		{json.put("mag", in);}
	public void setCdi(double in)		{json.put("cdi", in);}
	public void setMmi(double in)		{json.put("mmi", in);}
	public void setDmin(double in)		{json.put("dmin", in);}
	public void setRms(double in)		{json.put("rms", in);}
	public void setGap(double in)		{json.put("gap", in);}
	public void setLongitude(double in)	{json.put("longitude", in);}
	public void setLatitude(double in)	{json.put("latitude", in);}
	public void setDepth(double in)		{json.put("depth", in);}

	public void setPlace(String in)		{json.put("place", in);}
	public void setUrl(String in)		{json.put("url", in);}
	public void setDetail(String in)	{json.put("detail", in);}
	public void setAlert(String in)		{json.put("alert", in);}
	public void setStatus(String in)	{json.put("status", in);}
	public void setNet(String in)		{json.put("net", in);}
	public void setCode(String in)		{json.put("code", in);}
	public void setIds(String in)		{json.put("ids", in);}
	public void setSources(String in)	{json.put("sources", in);}
	public void setTypes(String in)		{json.put("types", in);}
	public void setMagType(String in)	{json.put("magType", in);}

	public void setTime(int in)			{json.put("time", in);}
	public void setUpdated(int in)		{json.put("updated", in);}
	public void setTz(int in)			{json.put("tz", in);}
	public void setFelt(int in)			{json.put("felt", in);}
	public void setTsunami(int in)		{json.put("tsunami", in);}
	public void setSig(int in)			{json.put("sig", in);}
	public void setNst(int in)			{json.put("nst", in);}

	// Getters
	public EventID getEventID()		{return eventID};

	public double getMag()			{return json.get("mag");}
	public double getCdi()			{return json.get("cdi");}
	public double getMmi()			{return json.get("mmi");}
	public double getDmin()			{return json.get("dmin");}
	public double getRms()			{return json.get("rms");}
	public double getGap()			{return json.get("gap");}
	public double getLongitude()	{return json.get("longitude");}
	public double getLatitude()		{return json.get("latitude");}
	public double getDepth()		{return json.get("depth");}

	public String getPlace()		{return json.get("place");}
	public String getUrl()			{return json.get("url");}
	public String getDetail()		{return json.get("detail");}
	public String getAlert()		{return json.get("alert");}
	public String getStatus()		{return json.get("status");}
	public String getNet()			{return json.get("net");}
	public String getCode()			{return json.get("code");}
	public String getIds()			{return json.get("ids");}
	public String getSources()		{return json.get("sources");}
	public String getTypes()		{return json.get("types");}
	public String getMagType()		{return json.get("magType");}

	public int getTime()			{return json.get("time");}
	public int getUpdated()			{return json.get("updated");}
	public int getTz()				{return json.get("tz");}
	public int getFelt()			{return json.get("felt");}
	public int getTsunami()			{return json.get("tsunami");}
	public int getSig()				{return json.get("sig");}
	public int getNst()				{return json.get("nst");}