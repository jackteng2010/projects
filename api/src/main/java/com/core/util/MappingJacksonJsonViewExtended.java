package com.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import com.core.exception.UserPermissionException;

public class MappingJacksonJsonViewExtended extends MappingJacksonJsonView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Object ep = model.get("exception");
		if(ep == null){
			result.put("status", "1");
			List<String> ignoreKeys = new ArrayList<String>();
			for (Map.Entry<String, Object> entry : model.entrySet()) {
				if(entry.getValue() instanceof BindingResult){
					String key = entry.getKey();
					ignoreKeys.add(key.substring(key.lastIndexOf(".")+1, key.length()));
				} else {
					result.put(entry.getKey(), entry.getValue());
				}
			}
			for(String s : ignoreKeys){
				result.remove(s);
			}
		} else {
			result.put("status", "0");
			if(ep instanceof UserPermissionException){
				result.put("msg", ((UserPermissionException)ep).getTipMsg());
			} else {
				result.put("msg","系统繁忙 请稍候再试");
			}
			((Exception)ep).printStackTrace();
		}
		
		String callback = request.getParameter("callback");
		//String json = new JSONObject(result).toString();
		String json = new ObjectMapper().writeValueAsString(result);
		if(callback != null && !callback.isEmpty()){
			json = callback + "(" + json + ")";
		}
		response.getWriter().write(json);
	}

}  