package com.catcher.javanium.p2p.utilities;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class Contains extends BaseMatcher<String> {

	String stringToMatch;

	public Contains(String stringToMatch){
		this.stringToMatch =  stringToMatch;
	}

	@Override
	public boolean matches(Object item) {
		return item.toString().contains(stringToMatch);
	}

	@Override
	public void describeTo(Description description) {}
}
