package com.crihexe.hiddenviewsmind.publisher.instagram.responses;

import com.crihexe.hiddenviewsmind.dto.InsightValue;

import java.util.List;

public class Insight {
	
	public String name;
	public String period;
	public List<InsightValue> values;
	
	@Override
	public String toString() {
		return "Insight [name=" + name + ", period=" + period + ", values=" + values + "]";
	}
	
}
