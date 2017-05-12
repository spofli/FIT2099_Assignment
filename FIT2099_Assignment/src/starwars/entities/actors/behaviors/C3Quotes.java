package starwars.entities.actors.behaviors;

/**
 * Behavior made to choose from a set of 5 random quotes for
 * C3P0 to say each turn
 * 
 * @author Seolhyun95
 *
 */
public class C3Quotes {
	
	/**
	 * Method to return a random quote from 5 with 20% chance of each quote.
	 * 
	 * @return string of quote to say
	 */
	public static String getQuote() {
		
		Double quoteNumber = Math.random();
		if (quoteNumber > 0.8) {
			return "C-3PO says \"We seem to be made to suffer. It's our lot in life.\"";
		}
		else if (quoteNumber > 0.6) {
			return "C-3PO says \"Perfect weather today.\"";
		}
		else if (quoteNumber > 0.4) {
			return "C-3PO says \" The odds of me successfully making through this game alive are 3720 to 1.\"";
		}
		else if (quoteNumber > 0.2) {
			return "C-3PO says \" R2-D2 where are you? \"";
		}
		else {
			return "C-3PO says \"I think I'm in trouble.";
		}
	
	}

}
