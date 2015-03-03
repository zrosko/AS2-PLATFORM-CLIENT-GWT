package hr.adriacomsoftware.inf.client.smartgwt.validators.iban;

import java.math.BigInteger;
import java.util.HashMap;

public class IBAN {
	public static void main(String[] args) {
		boolean ok = IBAN.isCheckDigitValid("HR2324110061100000043");
		System.out.println(ok);
	}
    /**
     * Determines if the given IBAN is valid based on the check digit.
     * To validate the checksum:
     * 1. Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid.
     * 2. Move the four initial characters to the end of the string.
     * 3. Replace the letters in the string with digits, expanding the string as necessary, such that A=10, B=11 and Z=35.
     * 4. Convert the string to an integer and mod-97 the entire number.
     * If the remainder is 1 you have a valid IBAN number.
     * @param iban
     * @return boolean indicating if specific IBAN has a valid check digit
     */
    public static boolean isCheckDigitValid(String iban) {
        if (null == iban) return true;
        if(iban.length()==0) return true;
        
        int validIBANLength = getValidIBANLength(iban);
        if (validIBANLength < 4) return false;
        if (iban.length() != validIBANLength) return false;
        
        BigInteger numericIBAN = getNumericIBAN(iban, false);

        int checkDigit = numericIBAN.mod(new BigInteger("97")).intValue();
        return checkDigit == 1;
    }
    
    /**
     * Using the IBAN.properties file gets the valid fixed length value for a country code.
     * Only uses the first 2 characters of the given string.
     * @param countryCode
     * @return
     */
    public static int getValidIBANLength(String countryCode) {
    	String code = countryCode.substring(0,2).toUpperCase();
      	String length = getCountriesLenght(code);//lgrid.getField(CountriesLengthXMLModel.LENGTH).getAttribute("length");//ResourceBundle.getBundle(IBAN.class.getCanonicalName()).getString("length."+code);
    	if (length == null) return -1;
    	return Integer.valueOf(length).intValue();
    }
    
    private static BigInteger getNumericIBAN(String iban, boolean isCheckDigitAtEnd) {
        String endCheckDigitIBAN = iban;
        if (!isCheckDigitAtEnd) {
            //Move first four characters to end of string to put check digit at end
            endCheckDigitIBAN = iban.substring(4) + iban.substring(0, 4);
        }
        StringBuffer numericIBAN = new StringBuffer();
        for (int i = 0; i < endCheckDigitIBAN.length(); i++) {
            if (Character.isDigit(endCheckDigitIBAN.charAt(i))) {
                numericIBAN.append(endCheckDigitIBAN.charAt(i));
            } else {
                numericIBAN.append(10 + getAlphabetPosition(endCheckDigitIBAN.charAt(i)));
            }
        }
        
        return new BigInteger(numericIBAN.toString());
    }

    private static int getAlphabetPosition(char letter) {
        return Character.valueOf(Character.toUpperCase(letter)).compareTo(Character.valueOf('A'));
    }
    
    private static String getCountriesLenght(String code){
    	HashMap<String,String> countriesLength = new HashMap<String,String>();
    	countriesLength.put("AD", "24");
    	countriesLength.put("AT", "20");
    	countriesLength.put("BE", "16");
    	countriesLength.put("BA", "20");
    	countriesLength.put("BG", "22");
    	countriesLength.put("CH", "21");
    	countriesLength.put("CY", "28");
    	countriesLength.put("CZ", "24");
    	countriesLength.put("DE", "22");
    	countriesLength.put("DK", "18");
    	countriesLength.put("EE", "20");
    	countriesLength.put("ES", "24");
    	countriesLength.put("FO", "18");
    	countriesLength.put("FI", "18");
    	countriesLength.put("FR", "27");
    	countriesLength.put("GB", "22");
    	countriesLength.put("GI", "23");
    	countriesLength.put("GL", "18");
    	countriesLength.put("GR", "27");
    	countriesLength.put("HU", "28");
    	countriesLength.put("HR", "21");
    	countriesLength.put("IE", "22");
    	countriesLength.put("IS", "26");
    	countriesLength.put("IT", "27");
    	countriesLength.put("LI", "21");
    	countriesLength.put("LT", "20");
    	countriesLength.put("LU", "20");
    	countriesLength.put("LV", "21");
    	countriesLength.put("MA", "24");
    	countriesLength.put("MC", "27");
    	countriesLength.put("MK", "19");
    	countriesLength.put("MT", "31");
    	countriesLength.put("NL", "18");
    	countriesLength.put("NO", "15");
    	countriesLength.put("PL", "28");
    	countriesLength.put("PT", "25");
    	countriesLength.put("RO", "24");
    	countriesLength.put("RS", "22");
    	countriesLength.put("SE", "24");
    	countriesLength.put("SI", "19");
    	countriesLength.put("SK", "24");
    	countriesLength.put("SM", "27");
    	countriesLength.put("TN", "24");
    	countriesLength.put("TR", "26");
    	return countriesLength.get(code);
    	
    }
}