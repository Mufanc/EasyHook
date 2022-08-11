package mufanc.easyhook.ksp_xposed

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class XposedEntryProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): XposedEntryProcessor {
        return XposedEntryProcessor(environment)
    }
}
