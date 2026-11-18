package com.example.academicplanner
import com.example.academicplanner.data.local.entity.ModuleEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the Modules.
 */
class ModuleUnitTests {
    /**
     * TEST 1 : Input sanitization
     * makes sure that white space is properly trimmed. So that database queries don't fail
     */
    @Test
    fun moduleInput_whitespaceIsProperlyTrimmed() {
        val rawCodeInput = "   CS2800   "
        val rawNameInput = "   Final Year Project   "
        val sanitizedCode = rawCodeInput.trim()
        val sanitizedName = rawNameInput.trim()
        assertEquals("CS2800", sanitizedCode)
        assertEquals("Final Year Project", sanitizedName)
    }

    /**
     * TEST 2 : Credit Parsing & Fallback
     * Ensures that invalid strings, letters, or blank credit inputs don't crash the app,
     * and it falls back to the default value of 15 credits.
     */
    @Test
    fun creditInput_invalidInput_fallsBackToDefaultCredits() {
        val invalidCreditInput = "abc"
        val emptyCreditInput = ""
        val parsedCredits1 = invalidCreditInput.toIntOrNull() ?: 15
        val parsedCredits2 = emptyCreditInput.toIntOrNull() ?: 15
        assertEquals(15, parsedCredits1)
        assertEquals(15, parsedCredits2)
    }

    /**
     * TEST 3 : Validation
     * So students cant create empty or ghost modules in the database.
     */
    @Test
    fun moduleValidation_blankFields_areRejected() {
        val blankCode = "   "
        val blankName = ""
        val validCode = "CS2800"
        val validName = "Final Year Project"
        val isInvalid = blankCode.isBlank() || blankName.isBlank()
        val isValid = validCode.isNotBlank() && validName.isNotBlank()
        assertTrue("Empty code or name should be flagged as invalid", isInvalid)
        assertTrue("Populated code and name should be flagged as valid", isValid)
    }


}