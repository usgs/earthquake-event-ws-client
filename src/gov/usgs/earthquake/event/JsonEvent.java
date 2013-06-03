package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSON Event class.
 */
public class JsonEvent {

	private JSONObject json;

	/**
	 * Construct a new JSONEvent from an existing json object.
	 * 
	 * @param json
	 */
	public JsonEvent(JSONObject json) {
		this.json = new JSONObject(json);
	}

	private JSONObject getProperties() {
		return JsonUtil.getJsonObject(json.get("properties"));
	}

	private JSONArray getCoordinates() {
		JSONObject geometry = JsonUtil.getJsonObject(json.get("geometry"));
		return JsonUtil.getJsonArray(geometry.get("coordinates"));
	}

	// Getters
	public EventId getEventId() {
		return new EventId(getNet(), getCode());
	}

	public BigDecimal getMag() {
		return JsonUtil.getBigDecimal(getProperties().get("mag"));
	}

	public BigDecimal getCdi() {
		return JsonUtil.getBigDecimal(getProperties().get("cdi"));
	}

	public BigDecimal getMmi() {
		return JsonUtil.getBigDecimal(getProperties().get("mmi"));
	}

	public BigDecimal getDmin() {
		return JsonUtil.getBigDecimal(getProperties().get("dmin"));
	}

	public BigDecimal getRms() {
		return JsonUtil.getBigDecimal(getProperties().get("rms"));
	}

	public BigDecimal getGap() {
		return JsonUtil.getBigDecimal(getProperties().get("gap"));
	}

	public BigDecimal getLongitude() {
		return JsonUtil.getBigDecimal(getCoordinates().get(0));
	}

	public BigDecimal getLatitude() {
		return JsonUtil.getBigDecimal(getCoordinates().get(1));
	}

	public BigDecimal getDepth() {
		return JsonUtil.getBigDecimal(getCoordinates().get(2));
	}

	public String getPlace() {
		return JsonUtil.getString(getProperties().get("place"));
	}

	public String getUrl() {
		return JsonUtil.getString(getProperties().get("url"));
	}

	public String getDetail() {
		return JsonUtil.getString(getProperties().get("detail"));
	}

	public String getAlert() {
		return JsonUtil.getString(getProperties().get("alert"));
	}

	public String getStatus() {
		return JsonUtil.getString(getProperties().get("status"));
	}

	public String getNet() {
		return JsonUtil.getString(getProperties().get("net"));
	}

	public String getCode() {
		return JsonUtil.getString(getProperties().get("code"));
	}

	public String getIds() {
		return JsonUtil.getString(getProperties().get("ids"));
	}

	public String getSources() {
		return JsonUtil.getString(getProperties().get("sources"));
	}

	public String getTypes() {
		return JsonUtil.getString(getProperties().get("types"));
	}

	public String getMagType() {
		return JsonUtil.getString(getProperties().get("magType"));
	}

	public Date getTime() {
		return JsonUtil.getDate(getProperties().get("time"));
	}

	public Date getUpdated() {
		return JsonUtil.getDate(getProperties().get("updated"));
	}

	public Integer getTz() {
		return JsonUtil.getInteger(getProperties().get("tz"));
	}

	public Integer getFelt() {
		return JsonUtil.getInteger(getProperties().get("felt"));
	}

	public Integer getTsunami() {
		return JsonUtil.getInteger(getProperties().get("tsunami"));
	}

	public Integer getSig() {
		return JsonUtil.getInteger(getProperties().get("sig"));
	}

	public Integer getNst() {
		return JsonUtil.getInteger(getProperties().get("nst"));
	}

}
