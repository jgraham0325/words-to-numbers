package org.jg.wordstonumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Testing algorithm to convert words in paragraph representing numbers into standard digit represenation
 * 
 * Note that currently all formatting and occurrences of "and" are getting stripped out. Not needed for current purposes
 * 
 * e.g. 
 * Input: "There were twenty five coins"
 * Output: "there were 25 coins"
 *
 */
public class WordsToNumbersUtil {
	final static List<String> allowedStrings = Arrays.asList("zero", "one", "two", "three", "four", "five", "six",
			"seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
			"seventeen", "eighteen", "nineteen", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety", "hundred", "thousand", "million", "billion", "trillion");

	public static void main(String[] args) {

		String sentence = "6.12 patients (seven knees) were excluded: three patients (three knees) had peri-prosthetic infection, one patient (two knees) died in circumstances unrelated to surgery, one patient (one knee) had a cerebrovascular accident and one patient (one knee) had severe spinal problems. A further seven patients (ten knees) were excluded as they did not attend the clinic. Consequently, 333 knees (207 patients) which were followed up beyond 12 months were included. A total of 81 TKRs were unilateral and 126 were bilateral, with an interval of between one and three weeks (typically two) between operations. There were 196 women and 11 men with a mean age of 68.7 years (54 to 87). The mean height and weight were 151.6 cm (138 to 174) and 60.4 kg (36 to 83). The mean body mass index (BMI) was 26.2 kg/m2 (18.0 to 37.0). All patients gave informed consent to participate in the study. Pre-operative information was gathered three days before surgery by an independent investigator (YGK), who recorded the range of movement (ROM), flexion contracture and the maximum flexion, and collected data from questionnaires. The pre-operative evaluation included the demography, diagnosis, medical history and physical examination, including the ROM, and collateral and antero-posterior stress tests. The passive, non-weight-bearing ROM was measured to the nearest 5� by a goniometer, with the patient supine. Patients were also evaluated by the knee and function scores of the American Knee Society (AKS),1 Western Ontario McMaster Universities Osteoarthritis Index (WOMAC)26 and the Short Form-36 (SF-36).27 All operations were performed by a single surgeon (TKK). A total of 172 knees were implanted with E-motion (FP type, Aesculap, Tuttlingen, Germany) and 161 with Genesis II (CR type in 112 knees and PS type in 49; Smith & Nephew, Memphis, Tennessee) arthroplasties. All operations used a medial parapatellar arthrotomy, and for 101 TKRs with the E-motion, computer-assisted navigation was used (Orthopilot 4.0, Aescalup, Germany). The other 71 TKRs with the E-motion and 161 with Genesis II, used conventional intramedullary guides for the femur and extramedullary guides for the tibia. Patellar resurfacing was routine and all implants were fixed with cement. The patients remained in hospital for two weeks, undergoing a standard rehabilitation programme, commencing on the first post-operative day with continuous passive movement. On the third day, they attended the rehabilitation department for training with crutches or walking aids. Between the fourth and the 14th days they had daily physiotherapy. Progression to maximum flexion was recorded with the goal of achieving 110� of flexion without a flexion contracture by discharge. Post-operative assessment was carried out by the same investigator (YGK) at three, six and 12 months, and yearly thereafter. Evaluations included ROM, AKS, WOMAC and SF-36 scores. In bilateral cases, AKS and WOMAC scores were measured separately for each knee...";
		String words = convertTextualNumbersInDocument(sentence);
		System.out.println("Paragraph before: " + sentence);
		System.out.println("Paragraph after:" + words);
	}

	/**
	 * Main driver method. Converts textual numbers (e.g. twenty five) to
	 * integers (e.g. 25)
	 * 
	 * Does not currently cater for decimal points. e.g. "five point two"
	 * 
	 * @param sentence
	 * @return
	 */
	private static String convertTextualNumbersInDocument(String sentence) {

		// remove formatting and joining words from text to make it easier to
		// process
		String[] words = cleanText(sentence);

		// look for groups of textual numbers
		List<Pair<Integer, Integer>> pairList = findTextualNumberPositions(words);

		// remove each of the textual number words (e.g. twenty five) and insert
		// the number (e.g. 25)
		words = removeWordsAndConvert(words, pairList);

		// clean up the result, removing nulls where the previously replaced
		// words were (in the case of having multiple words for a number, e.g.
		// twenty five)
		return arrayToStringWithoutNulls(words);
	}

	/**
	 * Takes a list of positions pairs for groups of textual numbers Removes
	 * them from the array holding all words (setting them to null, will be
	 * removed at a later step) and then converts the group (or singular if
	 * that's the case) into an actual number
	 * 
	 * @param words
	 * @param pairList
	 * @return
	 */
	private static String[] removeWordsAndConvert(String[] words, List<Pair<Integer, Integer>> pairList) {
		for (Pair<Integer, Integer> p : pairList) {
			String fullWords = getFullNumberAsText(words, p.getL(), p.getR());
			for (int i = p.getL(); i <= p.getR(); i++) {
				words[i] = null;
			}
			words[p.getL()] = String.valueOf(convertStringToNum(fullWords));
		}
		return words;
	}

	/**
	 * Look through a list of words and identify the start and end position of
	 * each textual word group e.g. "There are five hundred and two patients and
	 * the number of knees is fifty two" Would identify two position pairs, one
	 * for "five hundred and two" and the other for "fifty two"
	 * 
	 * @param words
	 * @return
	 */
	private static List<Pair<Integer, Integer>> findTextualNumberPositions(String[] words) {
		Integer start = 0;
		Integer finish = 0;

		List<Pair<Integer, Integer>> pairList = new ArrayList<Pair<Integer, Integer>>();

		for (int i = 0; i < words.length; i++) {
			if (allowedStrings.contains(words[i])) {
				start = i;
				finish = i;
				i++;
				while (i < words.length && allowedStrings.contains(words[i])) {
					finish++;
					i++;
				}
				pairList.add(new Pair<Integer, Integer>(start, finish));
			}
		}
		return pairList;
	}

	/**
	 * Removes all formatting and punction of text to make it easier to parse
	 * 
	 * @param sentence
	 * @return
	 */
	private static String[] cleanText(String sentence) {
		sentence = sentence.replaceAll("-", " ");
		sentence = sentence.replaceAll("[^a-zA-Z0-9.�%\\s]", "");
		sentence = sentence.replaceAll("\\s+", " ");
		sentence = sentence.replace(" and ", " ");
		sentence = sentence.toLowerCase();
		return sentence.split(" ");
	}

	/**
	 * Takes the list of words and extracts a textual word (possibly consisting
	 * of many words) e.g. words = "There were twenty two knees" start = 2
	 * finish = 3
	 * 
	 * @param words
	 * @param start
	 * @param finish
	 * @return
	 */
	private static String getFullNumberAsText(String[] words, Integer start, Integer finish) {
		String result = "";
		for (int i = start; i <= finish; i++) {
			result += " " + words[i];
		}
		return result;
	}

	/**
	 * Clears out any nulls from the array of words by creating a new string
	 * e.g. original: "There were twenty four patients" intermediate version:
	 * "there were 24 null patients" (the null was created in a previous step)
	 * final version: "there were 24 patients"
	 * 
	 * @param a
	 * @return
	 */
	public static String arrayToStringWithoutNulls(String[] a) {
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < a.length; i++) {
			String str = a[i];
			if (i == 0 && str != null) {
				result.append(a[i]);
			} else if (str != null) {
				result.append(" " + a[i]);
			}
		}

		return result.toString();
	}

	/**
	 * Logic for taking a textual number string and converting it into a number
	 * e.g. twenty five -> 25
	 * 
	 * This relies on there only being one textual number being processed. Steps
	 * prior to this deal with breaking a paragraph down into individual textual
	 * numbers, which could consist of a number of words.
	 * 
	 * @param input
	 * @return
	 */
	public static long convertStringToNum(String input) {
		boolean isValidInput = true;
		long result = 0;
		long finalResult = 0;

		if (input != null && input.length() > 0) {
			input = input.toLowerCase().replaceAll(" and", " ").replaceAll(",", "").replaceAll("-", " ");
			String[] splittedParts = input.trim().split("\\s+");

			for (String str : splittedParts) {
				if (!allowedStrings.contains(str)) {
					isValidInput = false;
					break;
				}
			}
			if (isValidInput) {
				for (String str : splittedParts) {
					if (str.equalsIgnoreCase("zero")) {
						result += 0;
					} else if (str.equalsIgnoreCase("one")) {
						result += 1;
					} else if (str.equalsIgnoreCase("two")) {
						result += 2;
					} else if (str.equalsIgnoreCase("three")) {
						result += 3;
					} else if (str.equalsIgnoreCase("four")) {
						result += 4;
					} else if (str.equalsIgnoreCase("five")) {
						result += 5;
					} else if (str.equalsIgnoreCase("six")) {
						result += 6;
					} else if (str.equalsIgnoreCase("seven")) {
						result += 7;
					} else if (str.equalsIgnoreCase("eight")) {
						result += 8;
					} else if (str.equalsIgnoreCase("nine")) {
						result += 9;
					} else if (str.equalsIgnoreCase("ten")) {
						result += 10;
					} else if (str.equalsIgnoreCase("eleven")) {
						result += 11;
					} else if (str.equalsIgnoreCase("twelve")) {
						result += 12;
					} else if (str.equalsIgnoreCase("thirteen")) {
						result += 13;
					} else if (str.equalsIgnoreCase("fourteen")) {
						result += 14;
					} else if (str.equalsIgnoreCase("fifteen")) {
						result += 15;
					} else if (str.equalsIgnoreCase("sixteen")) {
						result += 16;
					} else if (str.equalsIgnoreCase("seventeen")) {
						result += 17;
					} else if (str.equalsIgnoreCase("eighteen")) {
						result += 18;
					} else if (str.equalsIgnoreCase("nineteen")) {
						result += 19;
					} else if (str.equalsIgnoreCase("twenty")) {
						result += 20;
					} else if (str.equalsIgnoreCase("thirty")) {
						result += 30;
					} else if (str.equalsIgnoreCase("forty")) {
						result += 40;
					} else if (str.equalsIgnoreCase("fifty")) {
						result += 50;
					} else if (str.equalsIgnoreCase("sixty")) {
						result += 60;
					} else if (str.equalsIgnoreCase("seventy")) {
						result += 70;
					} else if (str.equalsIgnoreCase("eighty")) {
						result += 80;
					} else if (str.equalsIgnoreCase("ninety")) {
						result += 90;
					} else if (str.equalsIgnoreCase("hundred")) {
						result *= 100;
					} else if (str.equalsIgnoreCase("thousand")) {
						result *= 1000;
						finalResult += result;
						result = 0;
					} else if (str.equalsIgnoreCase("million")) {
						result *= 1000000;
						finalResult += result;
						result = 0;
					} else if (str.equalsIgnoreCase("billion")) {
						result *= 1000000000;
						finalResult += result;
						result = 0;
					} else if (str.equalsIgnoreCase("trillion")) {
						result *= 1000000000000L;
						finalResult += result;
						result = 0;
					}
				}

				finalResult += result;
				result = 0;
			}
		}
		return finalResult;

	}

}
