//package com.streamsoft.currencies.mapper;
//
//import org.springframework.stereotype.Component;
//
//import com.streamsoft.currencies.controller.CurrencyExchangeRateQuery;
//import com.streamsoft.currencies.controller.CurrencyExchangeRateQueryDto;
//import com.streamsoft.currencies.controller.CurrencyExchangeRateQueryParam;
//
//@Component
//public class CurrencyRatesRequestsMapper {
//	public CurrencyExchangeRateQuery mapToRequest(CurrencyExchangeRateQueryDto dto){
//		return new CurrencyExchangeRateQuery(dto.getValue(), new CurrencyExchangeRateQueryParam(dto.getRequestParamDto().getTable(), 
//				dto.getRequestParamDto().getCode(), dto.getRequestParamDto().getTopCount(), 
//				dto.getRequestParamDto().getDate(), dto.getRequestParamDto().getStartDate(),
//				dto.getRequestParamDto().getEndDate()));
//	}
//}