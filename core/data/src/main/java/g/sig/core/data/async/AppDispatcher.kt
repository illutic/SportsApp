package g.sig.core.data.async

sealed interface AppDispatcher {
    data object Main : AppDispatcher

    data object Default : AppDispatcher

    data object IO : AppDispatcher
}
