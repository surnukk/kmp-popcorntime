package ua.morozova.popcorntime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform