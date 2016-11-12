package org.jg.wordstonumbers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordsToNumbersUtilTest {

	@Test
	public void testValid() {
		assertTrue(WordsToNumbersUtil.convertStringToNum("one") == 1L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("twelve") == 12L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("thirty two") == 32L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("three hundred") == 300L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("four hundred and twenty") == 420L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("six hundred and fifty two") == 652L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("one thousand and twenty two") == 1022L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("five-million, three hundred and twenty thousand, two hundred and twelve") == 5320212L);
	}
	
	@Test
	public void testInvalid() {
		assertTrue(WordsToNumbersUtil.convertStringToNum("uno") == 0L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("nity four") == 0L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("234") == 0L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("three 23") == 0L);
		assertTrue(WordsToNumbersUtil.convertStringToNum("four )%^$%$") == 0L);
	}

}
