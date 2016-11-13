package org.jg.wordstonumbers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class WordsToNumbersUtilTest {

	private String input;
	private String expectedResult;

	public WordsToNumbersUtilTest(String input, String expectedResult) {
		this.input = input;
		this.expectedResult = expectedResult;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][] { 
			{ "one" , "1"}, 
			{ "twelve", "12"},
			{ "thirty two", "32"},
			{ "three hundred", "300"},
			{ "four hundred and twenty", "420"},
			{ "six hundred and fifty two", "652"},
			{ "one thousand and twenty two", "1022"},
			{ "uno", "uno"}, 
			{ "nity four", "nity 4"}, 
			{ "234", "234"}, 
			{ "three 23", "3 23"}, 
			{ "there were twenty five thousand people", "there were 25000 people"}, 
			{ "i was number one", "i was number 1"}, 
			{ "I was number one", "I was number 1"}, 
			{ "Twenty one People", "21 People"}, 
			{ "Twenty one Thousand and one People were there", "21001 People were there"}, 
			{ "it was twenty-four hours", "it was 24 hours"}, 
			{ "it was twenty-four.", "it was 24."}, 
			{ "it was twenty-test.", "it was 20 test."}, 
			{ "it was test-four.", "it was test-four."}, 
			{ "it was five-million, three hundred and twenty thousand, two hundred and twelve", "it was 5320212"}, 
			{ "it was fifty two, that was two", "it was 52, that was 2"}, 
			{ "it was fifty two: that was two", "it was 52: that was 2"}, 
			{ "it was one-hundred, and twenty pieces", "it was 120 pieces"}, 
			{ "it was fifty people, two more than me", "it was 50 people, 2 more than me"}, 
			{ "there were twenty-five, more", "there were 25, more"}, 
			{ "there were twenty-five-hours", "there were 25 hours"},
			{ "it was (seventy two knees) and (twenty one)", "it was (72 knees) and (21)"},
			{ "sun and moon are in line", "sun and moon are in line"},
			{ "and fifty and", "and 50 and"},
			{ "and fifty", "and 50"},
			{ "and and fifty", "and and 50"},
			{ "this (and fifty)", "this (and 50)"},
			{ "peas and rice", "peas and rice"},
			{ "twenty four mice and nine", "24 mice and 9"},
			{ "twenty four and pizza", "24 and pizza"},
			{ "and twenty four and pizza", "and 24 and pizza"},
			{ "and two thousand and four and pizza", "and 2004 and pizza"},
			{ "interval of between one and three weeks", "interval of between 1 and 3 weeks"},
			{ "interval of between twenty one and thirty three weeks", "interval of between 21 and 33 weeks"},
			{ "interval of between one hundred and one and thirty three weeks", "interval of between 101 and 33 weeks"},
			});
	}
	
	@Test
	   public void testWordsToNumbersUtil() {
	      assertEquals(expectedResult, WordsToNumbersUtil.convertTextualNumbersInDocument(input));
	   }
}
