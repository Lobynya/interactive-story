object Modules {
    val domain = MultiPlatformModule(
        name = ":mpp-library:domain",
        exported = true
    )
    object Features{
        val chat = MultiPlatformModule(
            name = ":mpp-library:features:chat"
        )
        val contents = MultiPlatformModule(
            name = ":mpp-library:features:contents"
        )
        val description = MultiPlatformModule(
            name = ":mpp-library:features:description"
        )
    }

}