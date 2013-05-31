package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Event Query class.
 */
public class EventQuery {

	private Date startTime = null;
	private Date endTime = null;
	private Date updatedAfter = null;

	private BigDecimal latitude = null;
	private BigDecimal longitude = null;
	private BigDecimal minRadius = null;
	private BigDecimal maxRadius = null;
	private BigDecimal minLatitude = null;
	private BigDecimal maxLatitude = null;
	private BigDecimal minLongitude = null;
	private BigDecimal maxLongitude = null;
	private BigDecimal minDepth = null;
	private BigDecimal maxDepth = null;
	private BigDecimal minMagnitude = null;
	private BigDecimal maxMagnitude = null;
	private BigDecimal minMmi = null;
	private BigDecimal maxMmi = null;
	private BigDecimal minCdi = null;
	private BigDecimal maxCdi = null;
	private BigDecimal minGap = null;
	private BigDecimal maxGap = null;
	private BigDecimal minSig = null;
	private BigDecimal maxSig = null;

	private Integer limit = null;
	private Integer offset = null;
	private Integer minFelt = null;

	private String magnitudeType = null;
	private String eventId = null;
	private String eventType = null;
	private String catalog = null;
	private String contributor = null;
	private String productType = null;

	private Boolean includeAllOrigins = null;
	private Boolean includeAllMagnitudes = null;
	private Boolean includeArrivals = null;
	private Boolean kmlAnimated = null;

	private OrderBy orderBy = null;
	private Format format = null;
	private KmlColorBy kmlColorBy = null;
	private ReviewStatus reviewStatus = null;
	private AlertLevel alertLevel = null;

	/**
	 * Construct a blank EventQuery.
	 */
	public EventQuery() {
	}

	// Getters
	public Date getStartTime()				{return startTime;}
	public Date getEndTime()				{return endTime;}
	public Date getUpdatedAfter()			{return updatedAfter;}

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

	public boolean getIncludeAllOrigins()	{return includeAllOrigins;}
	public boolean getIncludeAllMagnitudes(){return includeAllMagnitudes;}
	public boolean getIncludeArrivals()		{return includeArrivals;}
	public boolean getKmlAnimated()			{return kmlAnimated;}

	public OrderBy getOrderBy()				{return orderBy;}
	public Format getFormat()				{return format;}
	public KmlColorBy getKmlColorBy()		{return kmlColorBy;}
	public ReviewStatus getReviewStatus()	{return reviewStatus;}
	public AlertLevel getAlertLevel()		{return alertLevel;}

	// Setters
	public void setStartTime(Date in)			{startTime = in;}
	public void setEndTime(Date in)				{endTime = in;}
	public void setUpdatedAfter(Date in)		{updatedAfter = in;}

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

	public void setLimit(Integer in)				{limit = in;}
	public void setOffset(Integer in)				{offset = in;}
	public void setMinFelt(Integer in)				{minFelt = in;}

	public void setMagnitudeType(String in)		{magnitudeType = in;}
	public void setEventId(String in)			{eventId = in;}
	public void setEventType(String in)			{eventType = in;}
	public void setCatalog(String in)			{catalog = in;}
	public void setContributor(String in)		{contributor = in;}
	public void setProductType(String in)		{productType = in;}

	public void setIncludeAllOrigins(Boolean in){includeAllOrigins = in;}
	public void setIncludeAllMagnitudes(Boolean in){includeAllMagnitudes = in;}
	public void setIncludeArrivals(Boolean in)	{includeArrivals = in;}
	public void setKmlAnimated(Boolean in)		{kmlAnimated = in;}

	public void setOrderBy(OrderBy in)			{orderBy = in;}
	public void setFormat(Format in)			{format = in;}
	public void setKmlColorBy(KmlColorBy in)	{kmlColorBy = in;}
	public void setReviewStatus(ReviewStatus in){reviewStatus = in;}
	public void setAlertLevel(AlertLevel in)	{alertLevel = in;}
}
