package stateless.spring.security.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "code", "status", "message" })
public class ErrorResponseDto extends AbstractResponseDto{
	
}
