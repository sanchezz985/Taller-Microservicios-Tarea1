package com.consulting.mgt.springboot.acmehrsystem.utils;

public class HRUtils {

	private static int nextFacilitiesSerialNumber = 100;

	public static String nextFacilitiesSerialNumber() {

		return String.format("%05d", ++nextFacilitiesSerialNumber);
	}

}
