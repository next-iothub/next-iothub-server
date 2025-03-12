package com.wangsl.simulator;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectParam {

	public String username;
	public String password;

}
