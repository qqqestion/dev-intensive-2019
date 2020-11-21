package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_transliteration() {
        assertEquals(Utils.transliteration("Женя Стереотипов"), "Zhenya Stereotipov")
        assertEquals(Utils.transliteration("Amazing Петр", "_"), "Amazing_Petr")
    }

    @Test
    fun test_initials() {
        assertEquals("JD", Utils.toInitials("john", "doe"))
        assertEquals("J", Utils.toInitials("John", null))
        assertEquals(null, Utils.toInitials(null, null))
        assertEquals(null, Utils.toInitials(" ", ""))
    }

    @Test
    fun test_parseFullName() {
        assertEquals(Pair(null, null), Utils.parseFullName(null))
        assertEquals(Pair(null, null), Utils.parseFullName(""))
        assertEquals(Pair(null, null), Utils.parseFullName(" "))
        assertEquals(Pair("John", null), Utils.parseFullName("John"))
    }

    @Test
    fun test_humanizeDiff() {
        assertEquals(Date().add(1, TimeUnit.SECOND).humanizeDiff(), "только что")
        assertEquals(Date().add(-1, TimeUnit.MINUTE).humanizeDiff(), "минуту назад")
        assertEquals(Date().add(-2, TimeUnit.MINUTE).humanizeDiff(), "2 минуты назад")
        assertEquals(Date().add(-44, TimeUnit.MINUTE).humanizeDiff(), "44 минуты назад")
        // Ебаная параша
        assertEquals(Date().add(-2, TimeUnit.HOUR).humanizeDiff(), "2 часа назад")
        assertEquals(Date().add(-5, TimeUnit.DAY).humanizeDiff(), "5 дней назад")
        // Ебаная параша
        assertEquals(Date().add(2, TimeUnit.MINUTE).humanizeDiff(), "через 2 минуты")
        assertEquals(Date().add(7, TimeUnit.DAY).humanizeDiff(), "через 7 дней")
        assertEquals(Date().add(-400, TimeUnit.DAY).humanizeDiff(), "более года назад")
        assertEquals(Date().add(400, TimeUnit.DAY).humanizeDiff(), "более чем через год")
    }

    @Test
    fun test_factory() {
        val user1 = User.makeUser("Hello World")
        val user2 = User.makeUser(null)
        val user3 = User.makeUser("")
    }

    @Test
    fun test_decomposition() {
        val user1 = User.makeUser("Hello World")
        fun getUserInfo() = user1
        val (id, firstName, lastName) = getUserInfo()
        println("$id $firstName $lastName")
    }

    @Test
    fun test_copy() {
        val user1 = User.makeUser("Hello World")
        val user2 = user1.copy(id = "2", lastVisit = Date())

        if (user1.equals(user2)) {
            println("Equal")
        } else {
            println("Equal")
        }
        println("${user1.hashCode()} ${user2.hashCode()}")

    }

    @Test
    fun test_date() {
        val user1 = User.makeUser("Hello World")
        val user2 = user1.copy(id = "2", lastVisit = Date())
        val user3 = user1.copy(
            firstName = "Abbra",
            lastName = "Cadabra",
            lastVisit = Date().add(2, TimeUnit.HOUR)
        )
        val user4 = user1.copy(
            firstName = "Abbra",
            lastName = "Cadabra",
            lastVisit = Date().add(2, TimeUnit.DAY)
        )
        println(
            """
            ${user1.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent()
        )
    }

    @Test
    fun test_data_mapping() {
        val user = User.makeUser("Abbra Cadabra")

        val userView = user.copy(lastVisit = Date().add(-20, TimeUnit.DAY)).toUserView()
        println(user)
        println(userView)
    }

    @Test
    fun test_message_factory() {
        val user = User.makeUser("Hello World")
        val txtMessage =
            BaseMessage.makeMessage(user, Chat("0"), payload = "Hello, World!", type = "text")
        val imgMessage =
            BaseMessage.makeMessage(user, Chat("0"), payload = "Image url", type = "image")

        for (baseMessage in listOf(txtMessage, imgMessage)) {
            println(baseMessage.formatMessage())
            when (baseMessage) {
                is TextMessage -> println("This is text message")
                is ImageMessage -> println("This is image message")
            }
        }
    }
}