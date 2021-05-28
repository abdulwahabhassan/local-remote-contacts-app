package com.decagon.android.sq007

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    //TESTS FOR PHONE NUMBER VALIDATOR
    @Test
    fun phoneNumberValidator_CorrectStartingDigitOfZero_ReturnsTrue(){
        //Phone number can only start with zero for local calls
        Assert.assertTrue(Utils.phoneNumberValidator("08089455259"))
    }

    @Test
    fun phoneNumberValidator_IncorrectStartingDigit_ReturnsFalse() {
        //Returns false if phone number starts with any other number apart from zero for local calls
        Assert.assertFalse(Utils.phoneNumberValidator("10078365279"))
        Assert.assertFalse(Utils.phoneNumberValidator("8089455259"))
    }

    @Test
    fun phoneNumberValidator_CorrectCountryCode_ReturnsTrue() {
        // "+234" is the correct country code
        Assert.assertTrue(Utils.phoneNumberValidator("+2347039210649"))
    }

    @Test
    fun phoneNumberValidator_IncorrectCountryCode_ReturnsFalse() {
        //returns false if country code isn't "+234"
        Assert.assertFalse(Utils.phoneNumberValidator("+1237039210649"))
    }

    @Test
    fun phoneNumberValidator_ValidSecondDigitFromZero_ReturnsTrue() {
        //valid digits are only 7,8 and 9
        Assert.assertTrue(Utils.phoneNumberValidator("07089455259"))
        Assert.assertTrue(Utils.phoneNumberValidator("08089455259"))
        Assert.assertTrue(Utils.phoneNumberValidator("09089455259"))
    }
    @Test
    fun phoneNumberValidator_InvalidSecondDigitFromZero_ReturnsFalse() {
        //invalid second digits are any digits between 0 and 6 inclusive
        Assert.assertFalse(Utils.phoneNumberValidator("00089455259"))
        Assert.assertFalse(Utils.phoneNumberValidator("06089455259"))
        Assert.assertFalse(Utils.phoneNumberValidator("04189455259"))
    }

    @Test
    fun phoneNumberValidator_ValidSecondDigitFromCountryCode_ReturnsTrue() {
        //valid digits are only 7,8 and 9
        Assert.assertTrue(Utils.phoneNumberValidator("+2347089455259"))
        Assert.assertTrue(Utils.phoneNumberValidator("+2348089455259"))
        Assert.assertTrue(Utils.phoneNumberValidator("+2349089455259"))
    }
    @Test
    fun phoneNumberValidator_InvalidSecondDigitFromCountryCode_ReturnsFalse() {
        //invalid digits are any digits between 0 and 6 inclusive
        Assert.assertFalse(Utils.phoneNumberValidator("+23430089455259"))
        Assert.assertFalse(Utils.phoneNumberValidator("+2346089455259"))
        Assert.assertFalse(Utils.phoneNumberValidator("+2344189455259"))
    }

    @Test
    fun phoneNumberValidator_ValidThirdDigitFromZeroOrCountryCode_ReturnsTrue() {
        //Third digit can only be 0 or 1
        Assert.assertTrue(Utils.phoneNumberValidator("07078365279"))
        Assert.assertTrue(Utils.phoneNumberValidator("+2348145525912"))
    }

    @Test
    fun phoneNumberValidator_InvalidThirdDigitFromZeroOrCountryCode_ReturnsFalse() {
        //Will return false for any third digit that is not 0 or 1
        Assert.assertFalse(Utils.phoneNumberValidator("07778365279"))
        Assert.assertFalse(Utils.phoneNumberValidator("+2348245525986"))
    }

    @Test
    fun phoneNumberValidator_ValidNumberOfDigits_ReturnsTrue() {
        //Total number of digits can only be 13 for countryCode format and 11 for local calls format
        Assert.assertTrue(Utils.phoneNumberValidator("08089455259"))
        Assert.assertTrue(Utils.phoneNumberValidator("+2348089455259"))
    }

    @Test
    fun phoneNumberValidator_InvalidNumberOfDigits_ReturnsFalse() {
        //returns false if total number of digits is greater or less than 13 for countryCode format and
        // greater than or less than 11 for local calls format
        Assert.assertFalse(Utils.phoneNumberValidator("08089455"))
        Assert.assertFalse(Utils.phoneNumberValidator("+2348089455259265"))
    }


}