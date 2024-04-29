package com.glucode.about_you.engineers

import android.net.Uri
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStats
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class EngineersViewModelTest {

    private lateinit var engineersViewModel: EngineersViewModel

    @Before
    fun setUp() {
        engineersViewModel = EngineersViewModel()
    }

    @Test
    fun testSortingEngineersByYears() {
        // Given
        val unsortedEngineers = listOf(
            Engineer("John", quickStats = QuickStats(years = 5, coffees = 2000, bugs = 1000), imageUrl = Uri.parse("john_image_url")),
            Engineer("Alice", quickStats = QuickStats(years = 3, coffees = 3000, bugs = 2000), imageUrl = Uri.parse("alice_image_url")),
            Engineer("Bob", quickStats = QuickStats(years = 7, coffees = 300, bugs = 2000), imageUrl = Uri.parse("bob_image_url"))
        )
        engineersViewModel._engineers.value = unsortedEngineers

        // When
        engineersViewModel.sortingEngineersByYears()

        // Then
        val sortedEngineers = engineersViewModel.engineers.value
        assertEquals("Alice", sortedEngineers!![0].name)
        assertEquals("John", sortedEngineers[1].name)
        assertEquals("Bob", sortedEngineers[2].name)
    }

    @Test
    fun testSortEngineersByCoffee() {
        // Given
        val unsortedEngineers = listOf(
            Engineer("John", quickStats = QuickStats(years = 5, coffees = 2000, bugs = 1000), imageUrl = Uri.parse("john_image_url")),
            Engineer("Alice", quickStats = QuickStats(years = 3, coffees = 3000, bugs = 2000), imageUrl = Uri.parse("alice_image_url")),
            Engineer("Bob", quickStats = QuickStats(years = 7, coffees = 300, bugs = 2000), imageUrl = Uri.parse("bob_image_url"))
        )
        engineersViewModel._engineers.value = unsortedEngineers

        // When
        engineersViewModel.sortEngineersByCoffee()

        // Then
        val sortedEngineers = engineersViewModel.engineers.value
        assertEquals("Alice", sortedEngineers!![0].name)
        assertEquals("John", sortedEngineers[1].name)
        assertEquals("Bob", sortedEngineers[2].name)
    }

    @Test
    fun testSortEngineersByBugs() {
        // Given
        val unsortedEngineers = listOf(
            Engineer("John", quickStats = QuickStats(years = 5, coffees = 2000, bugs = 1000), imageUrl = Uri.parse("john_image_url")),
            Engineer("Alice", quickStats = QuickStats(years = 3, coffees = 3000, bugs = 2000), imageUrl = Uri.parse("alice_image_url")),
            Engineer("Bob", quickStats = QuickStats(years = 7, coffees = 300, bugs = 2000), imageUrl = Uri.parse("bob_image_url"))
        )
        engineersViewModel._engineers.value = unsortedEngineers

        // When
        engineersViewModel.sortEngineersByBugs()

        // Then
        val sortedEngineers = engineersViewModel.engineers.value
        assertEquals("Alice", sortedEngineers!![0].name)
        assertEquals("John", sortedEngineers[1].name)
        assertEquals("Bob", sortedEngineers[2].name)
    }

    @Test
    fun testUpdateEngineerImage() {
        // Given
        val engineerList = listOf(
            Engineer("John", imageUrl = Uri.parse("john_image_url")),
            Engineer("Alice", imageUrl = Uri.parse("alice_image_url")),
            Engineer("Bob", imageUrl = Uri.parse("bob_image_url"))
        )
        engineersViewModel._engineers.value = engineerList

        // When
        engineersViewModel.updateEngineerImage(0, Uri.parse("updated_john_image_url"))

        // Then
        val updatedImageUrl = engineersViewModel.engineers.value?.get(0)?.imageUrl
        assertEquals(Uri.parse("updated_john_image_url"), updatedImageUrl)
    }

}
