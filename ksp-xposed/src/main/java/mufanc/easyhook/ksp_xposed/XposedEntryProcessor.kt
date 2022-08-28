package mufanc.easyhook.ksp_xposed

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage
import mufanc.easyhook.api.IXposedEntry
import mufanc.easyhook.api.annotation.XposedEntry
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.listDirectoryEntries

class XposedEntryProcessor(
    private val environment: SymbolProcessorEnvironment
): SymbolProcessor {

    companion object {
        const val XPOSED_ENTRY = "_XposedInit"
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(XposedEntry::class.java.name)
            .filterIsInstance<KSClassDeclaration>()

        when (symbols.count()) {
            0 -> Unit
            1 -> {  // 注入 assets 并生成一个 _XposedInit 类
                val entry = symbols.first()

                var sourceRoot = Path((entry.location as FileLocation).filePath).parent
                while (sourceRoot.listDirectoryEntries("AndroidManifest.xml").isEmpty()) {
                    sourceRoot = sourceRoot.parent
                }

                Path("$sourceRoot${File.separator}assets").let { assets ->
                    assets.toFile().let {  // 如果 assets 目录不存在则创建
                        if ((it.exists() && it.isDirectory).not()) {
                            it.delete()
                            it.mkdirs()
                        }
                    }
                    assets.div("xposed_init").toFile()
                        .also {
                            environment.logger.warn("@Generated `xposed_init` at $it")
                        }.writeText("${entry.packageName.asString()}.${XPOSED_ENTRY}")
                }

                val manager = ClassName("mufanc.easyhook.api", "EasyHook")
                FileSpec.builder(entry.packageName.asString(), XPOSED_ENTRY)
                    .addType(
                        TypeSpec.classBuilder(XPOSED_ENTRY)
                            .addAnnotation(
                                AnnotationSpec.builder(ClassName("kotlin", "OptIn"))
                                    .addMember(
                                        "%T::class", ClassName("mufanc.easyhook.api.annotation", "InternalApi"))
                                    .build()
                            )
                            .addAnnotation(
                                AnnotationSpec.builder(ClassName("androidx.annotation", "Keep"))
                                    .build()
                            )
                            .addSuperinterface(IXposedEntry::class)
                            .addInitializerBlock(
                                CodeBlock.builder()
                                    .addStatement(
                                        "%T().onHook()",
                                        ClassName(entry.packageName.asString(), entry.simpleName.asString())
                                    )
                                    .build()
                            )
                            .addFunction(
                                FunSpec.builder(IXposedHookLoadPackage::handleLoadPackage.name)
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addParameter("param", XC_LoadPackage.LoadPackageParam::class)
                                    .addStatement("%T.dispatchLoadPackageEvent(param)", manager)
                                    .build()
                            )
                            .addFunction(
                                FunSpec.builder(IXposedHookZygoteInit::initZygote.name)
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addParameter("param", IXposedHookZygoteInit.StartupParam::class)
                                    .addStatement("%T.dispatchInitZygoteEvent(param)", manager)
                                    .build()
                            )
                            .build()
                    )
                    .indent(" ".repeat(4))
                    .build()
                    .let { code ->
                        environment.codeGenerator
                            .createNewFile(Dependencies.ALL_FILES, code.packageName, XPOSED_ENTRY)
                            .apply {
                                write(code.toString().toByteArray())
                                flush()
                                close()
                            }
                    }
            }
            else -> {  // `@XposedEntry` 注解多于一个
                environment.logger.error("Duplicate `@XposedEntry` annotations found at:\n ${
                    symbols.map { it.qualifiedName!!.asString() }.toList()
                }")
            }
        }

        return emptyList()
    }
}