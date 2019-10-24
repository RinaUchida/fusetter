package com.fusetter.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
class AppErrorController implements ErrorController {
	@Autowired
	ErrorAttributes errorAttributes;

	@RequestMapping("/error")
	Map<String, Object> error(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
		// エラー情報を取得する
		//		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(webRequest);
		Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, false);
		Throwable cause = errorAttributes.getError(webRequest);
		if (cause instanceof AppException) {
			// 例外に含まれるステータスコードとメッセージを返す
			AppException appException = (AppException) cause;
			response.setStatus(appException.getStatus());
			attributes.put("status", appException.getStatus());
			attributes.put("error", HttpStatus.valueOf(appException.getStatus()).getReasonPhrase());
			attributes.put("message", appException.getMessage());
		}
		return attributes;
	}

	@Override
    public String getErrorPath() {
        return "/error";
    }
}