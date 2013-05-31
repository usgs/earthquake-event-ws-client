package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

import org.json.simple.JSONObject;

/**
 * JSON Event class.
 */
public class JSONEvent {

	private EventID eventID;
	private JSONObject json;

	/**
	 * Construct a new JSONEvent from an existing json object.
	 *
	 * @param json
	 */
	public JSONEvent(JSONObject json) {
		this.json = new JSONObject(json);
		eventID = new EventID(this.getNet(), this.getCode());
	}

	private Integer getInteger(String key) {
		String value = (String) json.get(key);
		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private BigDecimal getDecimal(String key) {
		String value = (String) json.get(key);
		if (value == null) return null;
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			System.out.println(e);
			return null;
		}
	}

	private Date getTime(String key) {
		String value = (String) json.get(key);
		if (value == null) return null;
		long time = new BigDecimal(value).longValue();
		return new Date(time);
	}

	// Getters
	public EventID getEventID()			{return eventID;}

	public BigDecimal getMag()			{return getDecimal("mag");}
	public BigDecimal getCdi()			{return getDecimal("cdi");}
	public BigDecimal getMmi()			{return getDecimal("mmi");}
	public BigDecimal getDmin()			{return getDecimal("dmin");}
	public BigDecimal getRms()			{return getDecimal("rms");}
	public BigDecimal getGap()			{return getDecimal("gap");}
	public BigDecimal getLongitude()	{return getDecimal("longitude");}
	public BigDecimal getLatitude()		{return getDecimal("latitude");}
	public BigDecimal getDepth()		{return getDecimal("depth");}

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

	public Date getTime()				{return getTime("time");}
	public Date getUpdated()			{return getTime("updated");}

	public Integer getTz()				{return getInteger("tz");}
	public Integer getFelt()			{return getInteger("felt");}
	public Integer getTsunami()			{return getInteger("tsunami");}
	public Integer getSig()				{return getInteger("sig");}
	public Integer getNst()				{return getInteger("nst");}
}
