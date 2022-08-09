package mufanc.easyhook.ksp_xposed

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

@AutoService(SymbolProcessorProvider::class)
class XposedEntryProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): XposedEntryProcessor {
        return XposedEntryProcessor(environment)
    }
}
