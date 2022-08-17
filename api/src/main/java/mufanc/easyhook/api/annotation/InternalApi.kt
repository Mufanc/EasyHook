package mufanc.easyhook.api.annotation

@RequiresOptIn("Target is for internal use only", RequiresOptIn.Level.ERROR)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
annotation class InternalApi
