package com.springbazaar;

public interface PolicyEnforcement {
	boolean check(Object subject, Object resource, Object action, Object environment);
}