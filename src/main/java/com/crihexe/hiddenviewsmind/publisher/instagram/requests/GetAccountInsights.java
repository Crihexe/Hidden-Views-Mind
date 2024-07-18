package com.crihexe.hiddenviewsmind.publisher.instagram.requests;

import com.crihexe.japi.annotations.*;
import com.crihexe.japi.annotations.Method.Auth;
import com.crihexe.japi.annotations.Method.Methods;

@Method(method = Methods.GET, auth = Auth.QueryToken)
@Endpoint("/v20.0/{userID}/insights")
public class GetAccountInsights {
	
	@AuthKey(name = "access_token")
	public String _authorization;
	
	@PathParam(name = "userID")
	public String userID;
	
	@QueryParam(name = "metric", required = true)
	public String metric;
	
	@QueryParam(name = "period", required = true)
	public String period;
	
	public GetAccountInsights(String metric, String period, String userID) {
		this.metric = metric;
		this.period = period;
		this.userID = userID;
	}
	
}
