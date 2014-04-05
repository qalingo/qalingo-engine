package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class CustomerProductRatesViewBean extends AbstractViewBean implements Serializable {
	
    private static final long serialVersionUID = 1730581758207820023L;

	private Float avgQualityRates;
	private Float avgPriceRates;
	private Float avgValueRates;

	private Integer qualityRateCount;
	private Integer priceRateCount;
	private Integer valueRateCount;

	private Float avgRate;

	public Float getAvgQualityRates() {
		return avgQualityRates;
	}

	public void setAvgQualityRates(Float avgQualityRates) {
		this.avgQualityRates = avgQualityRates;
	}

	public Float getAvgPriceRates() {
		return avgPriceRates;
	}

	public void setAvgPriceRates(Float avgPriceRates) {
		this.avgPriceRates = avgPriceRates;
	}

	public Float getAvgValueRates() {
		return avgValueRates;
	}

	public void setAvgValueRates(Float avgValueRates) {
		this.avgValueRates = avgValueRates;
	}

	public Integer getQualityRateCount() {
		return qualityRateCount;
	}

	public void setQualityRateCount(Integer qualityRateCount) {
		this.qualityRateCount = qualityRateCount;
	}

	public Integer getPriceRateCount() {
		return priceRateCount;
	}

	public void setPriceRateCount(Integer priceRateCount) {
		this.priceRateCount = priceRateCount;
	}

	public Integer getValueRateCount() {
		return valueRateCount;
	}

	public void setValueRateCount(Integer valueRateCount) {
		this.valueRateCount = valueRateCount;
	}

	public Float getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Float avgRate) {
		this.avgRate = avgRate;
	}
	
	public Integer getAvgPriceStar(){
		return avgPriceRates.intValue();
	}
	
	public Integer getAvgValueStar(){
		return avgValueRates.intValue();
	}
	
	public Integer getAvgQualityStar(){
		return avgQualityRates.intValue();
	}
	
	public Integer getAvgRateStar(){
		return avgRate.intValue();
	}

	@Override
	public String toString() {
		return "CustomerProductRatesViewBean [avgQualityRates="
				+ avgQualityRates + ", avgPriceRates=" + avgPriceRates
				+ ", avgValueRates=" + avgValueRates + ", qualityRateCount="
				+ qualityRateCount + ", priceRateCount=" + priceRateCount
				+ ", valueRateCount=" + valueRateCount + ", avgRate=" + avgRate
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avgPriceRates == null) ? 0 : avgPriceRates.hashCode());
		result = prime * result
				+ ((avgQualityRates == null) ? 0 : avgQualityRates.hashCode());
		result = prime * result + ((avgRate == null) ? 0 : avgRate.hashCode());
		result = prime * result
				+ ((avgValueRates == null) ? 0 : avgValueRates.hashCode());
		result = prime * result
				+ ((priceRateCount == null) ? 0 : priceRateCount.hashCode());
		result = prime
				* result
				+ ((qualityRateCount == null) ? 0 : qualityRateCount.hashCode());
		result = prime * result
				+ ((valueRateCount == null) ? 0 : valueRateCount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerProductRatesViewBean other = (CustomerProductRatesViewBean) obj;
		if (avgPriceRates == null) {
			if (other.avgPriceRates != null)
				return false;
		} else if (!avgPriceRates.equals(other.avgPriceRates))
			return false;
		if (avgQualityRates == null) {
			if (other.avgQualityRates != null)
				return false;
		} else if (!avgQualityRates.equals(other.avgQualityRates))
			return false;
		if (avgRate == null) {
			if (other.avgRate != null)
				return false;
		} else if (!avgRate.equals(other.avgRate))
			return false;
		if (avgValueRates == null) {
			if (other.avgValueRates != null)
				return false;
		} else if (!avgValueRates.equals(other.avgValueRates))
			return false;
		if (priceRateCount == null) {
			if (other.priceRateCount != null)
				return false;
		} else if (!priceRateCount.equals(other.priceRateCount))
			return false;
		if (qualityRateCount == null) {
			if (other.qualityRateCount != null)
				return false;
		} else if (!qualityRateCount.equals(other.qualityRateCount))
			return false;
		if (valueRateCount == null) {
			if (other.valueRateCount != null)
				return false;
		} else if (!valueRateCount.equals(other.valueRateCount))
			return false;
		return true;
	}

}
