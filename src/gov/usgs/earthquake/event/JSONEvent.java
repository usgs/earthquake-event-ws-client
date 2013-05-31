package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSON Event class.
 */
public class JSONEvent {

	private JSONObject json;

	/**
	 * Construct a new JSONEvent from an existing json object.
	 *
	 * @param json
	 */
	public JSONEvent(JSONObject json) {
		this.json = new JSONObject(json);
	}

	private Date getTime(String key) {
		Long value = JSONUtil.getLong(getProperties().get(key));
		if (value == null) return null;
		return new Date(value);
	}

	private JSONObject getProperties() {
		return JSONUtil.getJSONObject(json.get("properties"));
	}

	private JSONArray getCoordinates() {
		JSONObject geometry = JSONUtil.getJSONObject(json.get("geometry"));
		return JSONUtil.getJSONArray(geometry.get("coordinates"));
	}

	// Getters
	public EventID getEventID()			{return new EventID(getNet(), getCode());}

	public BigDecimal getMag()			{return JSONUtil.getBigDecimal(getProperties().get("mag"));}
	public BigDecimal getCdi()			{return JSONUtil.getBigDecimal(getProperties().get("cdi"));}
	public BigDecimal getMmi()			{return JSONUtil.getBigDecimal(getProperties().get("mmi"));}
	public BigDecimal getDmin()			{return JSONUtil.getBigDecimal(getProperties().get("dmin"));}
	public BigDecimal getRms()			{return JSONUtil.getBigDecimal(getProperties().get("rms"));}
	public BigDecimal getGap()			{return JSONUtil.getBigDecimal(getProperties().get("gap"));}
	
	public BigDecimal getLongitude()	{return JSONUtil.getBigDecimal(getCoordinates().get(0));}
	public BigDecimal getLatitude()		{return JSONUtil.getBigDecimal(getCoordinates().get(1));}
	public BigDecimal getDepth()		{return JSONUtil.getBigDecimal(getCoordinates().get(2));}

	public String getPlace()			{return JSONUtil.getString(getProperties().get("place"));}
	public String getUrl()				{return JSONUtil.getString(getProperties().get("url"));}
	public String getDetail()			{return JSONUtil.getString(getProperties().get("detail"));}
	public String getAlert()			{return JSONUtil.getString(getProperties().get("alert"));}
	public String getStatus()			{return JSONUtil.getString(getProperties().get("status"));}
	public String getNet()				{return JSONUtil.getString(getProperties().get("net"));}
	public String getCode()				{return JSONUtil.getString(getProperties().get("code"));}
	public String getIds()				{return JSONUtil.getString(getProperties().get("ids"));}
	public String getSources()			{return JSONUtil.getString(getProperties().get("sources"));}
	public String getTypes()			{return JSONUtil.getString(getProperties().get("types"));}
	public String getMagType()			{return JSONUtil.getString(getProperties().get("magType"));}

	public Date getTime()				{return getTime("time");}
	public Date getUpdated()			{return getTime("updated");}

	public Integer getTz()				{return JSONUtil.getInteger(getProperties().get("tz"));}
	public Integer getFelt()			{return JSONUtil.getInteger(getProperties().get("felt"));}
	public Integer getTsunami()			{return JSONUtil.getInteger(getProperties().get("tsunami"));}
	public Integer getSig()				{return JSONUtil.getInteger(getProperties().get("sig"));}
	public Integer getNst()				{return JSONUtil.getInteger(getProperties().get("nst"));}
}
