package com.catcher.javanium.p2p.utilities;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class EqualsTo extends BaseMatcher<String> {

	String stringToMatch;

	public EqualsTo(String stringToMatch){
		this.stringToMatch =  stringToMatch;
	}

	@Override
	public boolean matches(Object item) {
		return stringToMatch.equals(item.toString());
	}

	@Override
	public void describeTo(Description description) {}
}
