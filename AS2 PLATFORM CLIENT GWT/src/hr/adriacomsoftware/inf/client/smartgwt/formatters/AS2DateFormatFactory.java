package hr.adriacomsoftware.inf.client.smartgwt.formatters;

public class AS2DateFormatFactory {
	public static final int CROATIAN = 0;
	public static final int EUROPEAN = 1;
	public static final int USA = 2;
	
	public static AS2DateFormat getDateFormat(int value) {
		AS2DateFormat dateFormat = null;
		switch (value) {
		case CROATIAN:
			dateFormat = new CroatianDateFormat(true);
			break;
		case EUROPEAN:
//			car = new SedanCar();
			break;

		case USA:
//			car = new LuxuryCar();
			break;

		default:
			// throw some exception
			break;
		}
		return dateFormat;
	}
	
	public static AS2DateFormat getDateTimeFormat(int value) {
		AS2DateFormat dateFormat = null;
		switch (value) {
		case CROATIAN:
			dateFormat = new CroatianDateFormat(false);
			break;
		case EUROPEAN:
//			car = new SedanCar();
			break;

		case USA:
//			car = new LuxuryCar();
			break;

		default:
			// throw some exception
			break;
		}
		return dateFormat;
	}

}
