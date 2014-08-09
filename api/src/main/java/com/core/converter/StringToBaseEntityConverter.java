package com.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.core.bean.BaseEntity;
import com.google.gson.Gson;

public class StringToBaseEntityConverter implements ConverterFactory<String, BaseEntity>{
	
	@Override
	public <T extends BaseEntity> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToBaseEntity<T>(targetType);
	}

	private final static class StringToBaseEntity<T extends BaseEntity> implements Converter<String, T> {

		private final Class<T> targetType;

		public StringToBaseEntity(Class<T> targetType) {
			this.targetType = targetType;
		}

		@Override
		public T convert(String source) {
			if(source != null && !source.isEmpty()){
				try{
					return new Gson().fromJson(source, targetType);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}