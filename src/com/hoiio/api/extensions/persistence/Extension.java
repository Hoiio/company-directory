package com.hoiio.api.extensions.persistence;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Extension {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long ID;
	
	@Persistent
	private String key;
	
	@Persistent
	private String forwardTo;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getForwardTo() {
		return forwardTo;
	}
	public void setForwardTo(String forwardTo) {
		this.forwardTo = forwardTo;
	}
}
