package gov.usgs.earthquake.event;

import java.math.BigDecimal;

/**
 * Event Query class.
 */
public class EventQuery {

	private long
		startTime, endTime,
		updatedAfter;

	private BigDecimal
		latitude, longitude,
		minRadius, maxRadius,
		minLatitude, maxLatitude,
		minLongitude, maxLongitude,
		minDepth, maxDepth,
		minMagnitude, maxMagnitude,
		minMmi, maxMmi,
		minCdi, maxCdi,
		minGap, maxGap,
		minSig, maxSig;

	private int
		limit, offset,
		minFelt;

	private String
		magnitudeType,
		eventId, eventType,
		catalog,
		contributor,
		productType;

	private boolean
		includeAllOrigins,
		includeAllMagnitudes,
		includeArrivals,
		kmlAnimated;

	private enum OrderBy {
		TIME, TIME_ASC, MAGNITUDE, MAGNITUDE_ASC
	}
	private OrderBy orderBy;

	private enum Format {
		QUAKEML, CSV, GEOJSON, KML
	}
	private Format format;

	private enum KmlColorBy {
		AGE, DEPTH
	}
	private KmlColorBy kmlColorBy;

	private enum ReviewStatus {
		ALL, AUTOMATIC, REVIEWED
	}
	private ReviewStatus reviewStatus;

	private enum AlertLevel {
		ALL, GREEN, YELLOW, ORANGE, RED
	}
	private AlertLevel alertLevel;

	/**
	 * Construct a blank EventQuery.
	 */
	public EventQuery() {
	}

	// Getters
	public long getStartTime()				{return startTime;}
	public long getEndTime()				{return endTime;}
	public long getUpdatedAfter()			{return updatedAfter;}

	public BigDecimal getLatitude()			{return latitude;}
	public BigDecimal getLongitude()		{return longitude;}
	public BigDecimal getMinRadius()		{return minRadius;}
	public BigDecimal getMaxRadius()		{return maxRadius;}
	public BigDecimal getMinLatitude()		{return minLatitude;}
	public BigDecimal getMaxLatitude()		{return maxLatitude;}
	public BigDecimal getMinLongitude()		{return minLongitude;}
	public BigDecimal getMaxLongitude()		{return maxLongitude;}
	public BigDecimal getMinDepth()			{return minDepth;}
	public BigDecimal getMaxDepth()			{return maxDepth;}
	public BigDecimal getMinMagnitude()		{return minMagnitude;}
	public BigDecimal getMaxMagnitude()		{return maxMagnitude;}
	public BigDecimal getMinMmi()			{return minMmi;}
	public BigDecimal getMaxMmi()			{return maxMmi;}
	public BigDecimal getMinCdi()			{return minCdi;}
	public BigDecimal getMaxCdi()			{return maxCdi;}
	public BigDecimal getMinGap()			{return minGap;}
	public BigDecimal getMaxGap()			{return maxGap;}
	public BigDecimal getMinSig()			{return minSig;}
	public BigDecimal getMaxSig()			{return maxSig;}

	public int getLimit()					{return limit;}
	public int getOffset()					{return offset;}
	public int getMinFelt()					{return minFelt;}

	public String getMagnitudeType()		{return magnitudeType;}
	public String getEventId()				{return eventId;}
	public String getEventType()			{return eventType;}
	public String getCatalog()				{return catalog;}
	public String getContributor()			{return contributor;}
	public String getProductType()			{return productType;}

	public boolean getIncludeAllOrigins()		{return includeAllOrigins;}
	public boolean getIncludeAllMagnitudes()	{return includeAllMagnitudes;}
	public boolean getIncludeArrivals()		{return includeArrivals;}
	public boolean getKmlAnimated()			{return kmlAnimated;}

	public String getOrderBy()				{return orderBy.name().toLowerCase().replace("_", "-");}
	public String getFormat()				{return format.name().toLowerCase();}
	public String getKmlColorBy()			{return kmlColorBy.name().toLowerCase();}
	public String getReviewStatus()			{return reviewStatus.name().toLowerCase();}
	public String getAlertLevel()			{return alertLevel.name().toLowerCase();}

	// Setters
	public void setStartTime(long in)			{startTime = in;}
	public void setEndTime(long in)				{endTime = in;}
	public void setUpdatedAfter(long in)		{updatedAfter = in;}

	public void setLatitude(BigDecimal in)		{latitude = in;}
	public void setLongitude(BigDecimal in)		{longitude = in;}
	public void setMinRadius(BigDecimal in)		{minRadius = in;}
	public void setMaxRadius(BigDecimal in)		{maxRadius = in;}
	public void setMinLatitude(BigDecimal in)	{minLatitude = in;}
	public void setMaxLatitude(BigDecimal in)	{maxLatitude = in;}
	public void setMinLongitude(BigDecimal in)	{minLongitude = in;}
	public void setMaxLongitude(BigDecimal in)	{maxLongitude = in;}
	public void setMinDepth(BigDecimal in)		{minDepth = in;}
	public void setMaxDepth(BigDecimal in)		{maxDepth = in;}
	public void setMinMagnitude(BigDecimal in)	{minMagnitude = in;}
	public void setMaxMagnitude(BigDecimal in)	{maxMagnitude = in;}
	public void setMinMmi(BigDecimal in)		{minMmi = in;}
	public void setMaxMmi(BigDecimal in)		{maxMmi = in;}
	public void setMinCdi(BigDecimal in)		{minCdi = in;}
	public void setMaxCdi(BigDecimal in)		{maxCdi = in;}
	public void setMinGap(BigDecimal in)		{minGap = in;}
	public void setMaxGap(BigDecimal in)		{maxGap = in;}
	public void setMinSig(BigDecimal in)		{minSig = in;}
	public void setMaxSig(BigDecimal in)		{maxSig = in;}

	public void setLimit(int in)				{limit = in;}
	public void setOffset(int in)				{offset = in;}
	public void setMinFelt(int in)				{minFelt = in;}

	public void setMagnitudeType(String in)		{magnitudeType = in;}
	public void setEventId(String in)			{eventId = in;}
	public void setEventType(String in)			{eventType = in;}
	public void setCatalog(String in)			{catalog = in;}
	public void setContributor(String in)		{contributor = in;}
	public void setProductType(String in)		{productType = in;}

	public void setIncludeAllOrigins(boolean in)	{includeAllOrigins = in;}
	public void setIncludeAllMagnitudes(boolean in){includeAllMagnitudes = in;}
	public void setIncludeArrivals(boolean in)		{includeArrivals = in;}
	public void setKmlAnimated(boolean in)			{kmlAnimated = in;}

	public void setOrderBy(String in)			{orderBy = OrderBy.valueOf(in.replace("-", "_").toUpperCase());}
	public void setFormat(String in)			{format = Format.valueOf(in.toUpperCase());}
	public void setKmlColorBy(String in)		{kmlColorBy = KmlColorBy.valueOf(in.toUpperCase());}
	public void setReviewStatus(String in)		{reviewStatus = ReviewStatus.valueOf(in.toUpperCase());}
	public void setAlertLevel(String in)		{alertLevel = AlertLevel.valueOf(in.toUpperCase());}
}
