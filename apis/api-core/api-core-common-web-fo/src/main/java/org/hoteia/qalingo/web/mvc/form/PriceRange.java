package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceRange implements Serializable{
	private static final long serialVersionUID = -7890013741566137517L;
	
	private Double start;
	private Double end;

	public Double getStart() {
		return start;
	}

	public void setStart(Double start) {
		this.start = start;
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(Double end) {
		this.end = end;
	}
	
	public BigDecimal getEndValue(){
		return new BigDecimal(end);
	}
	
	public BigDecimal getStartValue(){
		return new BigDecimal(start);
	}

	@Override
	public String toString() {
		return "PriceRange [start=" + start + ", end=" + end + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(end);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(start);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		PriceRange other = (PriceRange) obj;
		if (Double.doubleToLongBits(end) != Double.doubleToLongBits(other.end))
			return false;
		if (Double.doubleToLongBits(start) != Double
				.doubleToLongBits(other.start))
			return false;
		return true;
	}
	
	
}
