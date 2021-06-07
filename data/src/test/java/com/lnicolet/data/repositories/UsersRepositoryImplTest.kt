package com.lnicolet.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.lnicolet.data.UsersApi
import com.lnicolet.data.entities.UserEntity
import com.lnicolet.data.mappers.AddressEntityMapper
import com.lnicolet.data.mappers.CompanyEntityMapper
import com.lnicolet.data.mappers.GeoEntityMapper
import com.lnicolet.data.mappers.UserEntityMapper
import com.lnicolet.data.utils.RxSchedulerRule
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertFailsWith


class UsersRepositoryImplTest {

    companion object {
        private const val USERS_RESPONSE_OK = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Leanne Graham\",\n" +
            "    \"username\": \"Bret\",\n" +
            "    \"email\": \"Sincere@april.biz\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Kulas Light\",\n" +
            "      \"suite\": \"Apt. 556\",\n" +
            "      \"city\": \"Gwenborough\",\n" +
            "      \"zipcode\": \"92998-3874\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-37.3159\",\n" +
            "        \"lng\": \"81.1496\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"1-770-736-8031 x56442\",\n" +
            "    \"website\": \"hildegard.org\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Romaguera-Crona\",\n" +
            "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
            "      \"bs\": \"harness real-time e-markets\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"name\": \"Ervin Howell\",\n" +
            "    \"username\": \"Antonette\",\n" +
            "    \"email\": \"Shanna@melissa.tv\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Victor Plains\",\n" +
            "      \"suite\": \"Suite 879\",\n" +
            "      \"city\": \"Wisokyburgh\",\n" +
            "      \"zipcode\": \"90566-7771\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-43.9509\",\n" +
            "        \"lng\": \"-34.4618\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"010-692-6593 x09125\",\n" +
            "    \"website\": \"anastasia.net\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Deckow-Crist\",\n" +
            "      \"catchPhrase\": \"Proactive didactic contingency\",\n" +
            "      \"bs\": \"synergize scalable supply-chains\"\n" +
            "    }\n" +
            "  }" +
            "]"
        private const val USERS_RESPONSE_EMPTY = "[]"
        private const val USERS_RESPONSE_ERROR = "{}"
        private const val USER_RESPONSE_OK = "{\n" +
            "  \"id\": 4,\n" +
            "  \"name\": \"Patricia Lebsack\",\n" +
            "  \"username\": \"Karianne\",\n" +
            "  \"email\": \"Julianne.OConner@kory.org\",\n" +
            "  \"address\": {\n" +
            "    \"street\": \"Hoeger Mall\",\n" +
            "    \"suite\": \"Apt. 692\",\n" +
            "    \"city\": \"South Elvis\",\n" +
            "    \"zipcode\": \"53919-4257\",\n" +
            "    \"geo\": {\n" +
            "      \"lat\": \"29.4572\",\n" +
            "      \"lng\": \"-164.2990\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"phone\": \"493-170-9623 x156\",\n" +
            "  \"website\": \"kale.biz\",\n" +
            "  \"company\": {\n" +
            "    \"name\": \"Robel-Corkery\",\n" +
            "    \"catchPhrase\": \"Multi-tiered zero tolerance productivity\",\n" +
            "    \"bs\": \"transition cutting-edge web services\"\n" +
            "  }\n" +
            "}"
        private const val USER_RESPONSE_ERROR = "[\n" +
            "  \"id\": 4,\n" +
            "  \"name\": \"Patricia Lebsack\",\n" +
            "  \"username\": \"Karianne\",\n" +
            "  \"email\": \"Julianne.OConner@kory.org\",\n" +
            "  \"address\": {\n" +
            "    \"street\": \"Hoeger Mall\",\n" +
            "    \"suite\": \"Apt. 692\",\n" +
            "    \"city\": \"South Elvis\",\n" +
            "    \"zipcode\": \"53919-4257\",\n" +
            "    \"geo\": {\n" +
            "      \"lat\": \"29.4572\",\n" +
            "      \"lng\": \"-164.2990\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"phone\": \"493-170-9623 x156\",\n" +
            "  \"website\": \"kale.biz\",\n" +
            "  \"company\": {\n" +
            "    \"name\": \"Robel-Corkery\",\n" +
            "    \"catchPhrase\": \"Multi-tiered zero tolerance productivity\",\n" +
            "    \"bs\": \"transition cutting-edge web services\"\n" +
            "  }\n" +
            "]"
    }

    @get:Rule
    val rxRule = RxSchedulerRule()
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner
    @Mock
    lateinit var usersApi: UsersApi

    private lateinit var usersEntityMapper: UserEntityMapper
    private lateinit var geoEntityMapper: GeoEntityMapper
    private lateinit var addressEntityMapper: AddressEntityMapper
    private lateinit var companyEntityMapper: CompanyEntityMapper

    private lateinit var repository: UsersRepositoryImpl

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        geoEntityMapper = GeoEntityMapper()
        addressEntityMapper = AddressEntityMapper(geoEntityMapper)
        companyEntityMapper = CompanyEntityMapper()
        usersEntityMapper = UserEntityMapper(addressEntityMapper, companyEntityMapper)

        repository = UsersRepositoryImpl(usersApi, usersEntityMapper)
        setupLifeCycleOwner()
    }

    private fun setupLifeCycleOwner() {
        val lifecycle = LifecycleRegistry(lifeCycleOwner)
        Mockito.`when`(lifeCycleOwner.lifecycle).thenReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun `verify that users are fetched correctly with ok response`() {
        val listType = object : TypeToken<ArrayList<UserEntity>>() {}.type
        val response =
            GsonBuilder().create().fromJson<ArrayList<UserEntity>>(USERS_RESPONSE_OK, listType)

        Mockito.`when`(usersApi.getUsers())
            .thenReturn(Single.just(response))

        val observer = repository.getUsers().test()
        observer.awaitTerminalEvent()

        observer
            .assertComplete()
            .assertNoErrors()
            .assertNoTimeout()
            .assertValue {
                it.isNotEmpty()
            }
    }

    @Test
    fun `verify that users are empty with EMPTY response`() {
        val listType = object : TypeToken<ArrayList<UserEntity>>() {}.type
        val response =
            GsonBuilder().create().fromJson<ArrayList<UserEntity>>(USERS_RESPONSE_EMPTY, listType)

        Mockito.`when`(usersApi.getUsers())
            .thenReturn(Single.just(response))

        val observer = repository.getUsers().test()
        observer.awaitTerminalEvent()

        observer
            .assertComplete()
            .assertNoErrors()
            .assertNoTimeout()
            .assertValue {
                it.isEmpty()
            }
    }

    @Test
    fun `verify that exception is thrown with wrong response`() {
        val listType = object : TypeToken<ArrayList<UserEntity>>() {}.type

        assertFailsWith(JsonSyntaxException::class) {
            GsonBuilder().create()
                .fromJson<ArrayList<UserEntity>>(USERS_RESPONSE_ERROR, listType)
        }
    }

    @Test
    fun `verify that user is fetch correctly given an ID`() {
        val response = GsonBuilder().create()
            .fromJson<UserEntity>(USER_RESPONSE_OK, UserEntity::class.java)

        Mockito.`when`(usersApi.getUsersById(4))
            .thenReturn(Single.just(response))

        val observer = repository.getUsersById(4).test()
        observer.awaitTerminalEvent()

        observer
            .assertNoErrors()
            .assertComplete()
            .assertNoTimeout()
            .assertValue {
                it.id == 4
            }
    }

    @Test
    fun `verify that exception is thrown with bad response`() {
        assertFailsWith(JsonSyntaxException::class) {
            GsonBuilder().create()
                .fromJson<UserEntity>(USER_RESPONSE_ERROR, UserEntity::class.java)
        }
    }

}