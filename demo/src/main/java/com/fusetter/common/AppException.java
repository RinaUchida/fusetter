package com.fusetter.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException {

	//TODO　このエラーが発生した時だけ、ログに出力したくないんだができるだろうか。

	final int status;

	public AppException(int status, String message) {
		super(message);
		this.status = status;
	}

	public AppException(int status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}

}
