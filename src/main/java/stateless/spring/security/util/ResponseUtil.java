package stateless.spring.security.util;

import stateless.spring.security.dto.response.ErrorResponseDto;
import stateless.spring.security.dto.response.SuccessResponseDto;

public class ResponseUtil {

	public static SuccessResponseDto<Object> success(){
		return new SuccessResponseDto<Object>();
	}
	
	public static ErrorResponseDto error(){
		return new ErrorResponseDto();
	}
}
